package pl.joboffers.cache.redis;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.infrastructure.loginandregister.controller.JwtResultDto;

import java.time.Duration;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RedisIntegrationTest extends BaseIntegrationTest {

    @Container
    private static final GenericContainer<?> REDIS;

    @SpyBean
    OfferFacade offerFacade;

    @Autowired
    CacheManager cacheManager;

    static {
        REDIS = new GenericContainer<>("redis").withExposedPorts(6379);
        REDIS.start();
    }

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.port", () -> REDIS.getFirstMappedPort().toString());
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> "PT1S");
    }

    @Test
    public void should_save_fetched_offers_to_cache_and_invalidate_by_the_time_to_live() throws Exception {
        // step 1 register user
        // given && when
        ResultActions performFailedLogin = mockMvc.perform(post("/register")
                .content(("""
                        {
                        "username" : "testUsername",
                        "password" : "testPassword"
                        }
                                     """.trim())).contentType(MediaType.APPLICATION_JSON));
        //then
        performFailedLogin.andExpect(status().isCreated());

        // step 2 login user and get token
        // given && when
        ResultActions performSuccessLogin = mockMvc.perform(post("/token")
                .content(("""
                        {
                        "username" : "testUsername",
                        "password" : "testPassword"
                        }
                                     """.trim())).contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcSuccessLoginResult = performSuccessLogin.andExpect(status().isOk()).andReturn();
        String jsonToStringSuccess = mvcSuccessLoginResult.getResponse().getContentAsString();
        JwtResultDto jwtResultDto = objectMapper.readValue(jsonToStringSuccess, JwtResultDto.class);
        String token = jwtResultDto.token();

        //step 3 should save offers to cache
        // given && when
        ResultActions performGetAllOffers = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        //then
        performGetAllOffers.andExpect(status().isOk()).andReturn();
        verify(offerFacade, times(1)).findAllOffers();
        assertThat(cacheManager.getCacheNames().contains("jobOffers")).isTrue();


        //step 4 cache should be invalidated by the time to live
        // given && when && then
        await()
                .atMost(Duration.ofSeconds(4))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(()-> {
                    mockMvc.perform(get("/offers")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON));
                    verify(offerFacade, atLeast(2)).findAllOffers();
                });
    }
}
