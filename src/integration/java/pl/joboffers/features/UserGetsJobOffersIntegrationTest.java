package pl.joboffers.features;


import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.TestOffersDto;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;
import pl.joboffers.infrastructure.offer.scheduler.OfferScheduler;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserGetsJobOffersIntegrationTest extends BaseIntegrationTest implements TestOffersDto {
    @Autowired
    OfferScheduler offerScheduler;

    @Container
    public static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("offer.http.client.config.port", ()-> wireMockServer.getPort());
        registry.add("offer.http.client.config.uri", ()-> WIRE_MOCK_HOST);
    }

    @Test
    public void should_user_see_offers_but_have_to_be_logged_in_beforehand_and_external_server_should_have_some_offers() throws Exception {
//        1. there are no offers in external HTTP server
        //given&when&then
        wireMockServer.stubFor(WireMock.get("/offers")
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", "application/json")
                                .withBody(bodyWithZeroOffers())));


//        2. scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //given&when
        List<SaveOfferResultDto> fetchedOffers = offerScheduler.fetchAllOffersAndSaveIfNotExist();
        //then
        assertThat(fetchedOffers).isEmpty();


//        3. user tries to get a JWT Token using POST /token with some username(testUsername) and password(testPassword) and gets unauthorized(401)
        //given & when
        ResultActions performLoginFailed = mockMvc.perform(post("/token").content(
                """
                        {
                            "username" : "testUsername",
                            "password" : "testPassword"
                        }
                        """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        performLoginFailed.andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                                {
                                "message" : "Bad credentials",
                                "status" : "UNAUTHORIZED"
                                }
                """.trim()));


//        4. user tries to see offers with no token using GET /offers and get unauthorized(401)
//        5. user creates account using  POST /register, providing login and password
//        6. user get token using POST /token providing login and password and system returned OK (200) and a JWT token code


//        7. user made GET /offers with token and got returned 200 (OK) and 0 offers
        //given&when
        ResultActions performGetAllOffers = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResult = performGetAllOffers.andExpect(status().isOk()).andReturn();
        String jsonResult = mvcResult.getResponse().getContentAsString();
        List<SaveOfferResultDto> offers = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });
        assertThat(offers).hasSize(0);

//        8. there are two offers in external http server
        //given&when&then

        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoOffers())));


//        9. scheduler ran 2nd time and made GET to external server and system added two new offers with ids 1, 2 to DB
        //given&when
        List<SaveOfferResultDto> fetchedOffers2 = offerScheduler.fetchAllOffersAndSaveIfNotExist();
        //then
        assertThat(fetchedOffers2).hasSize(2);


//        10. user tries GET /offers with token and gets 200(OK) with 2 offers with ids 1, 2
        //given&when
        ResultActions performGetAllOffers2 = mockMvc.perform(get("/offers"));
        //then
        MvcResult mvcResult2 = performGetAllOffers2.andExpect(status().isOk()).andReturn();
        String jsonResult2 = mvcResult2.getResponse().getContentAsString();
        List<SaveOfferResultDto> offers2 = objectMapper.readValue(jsonResult2, new TypeReference<>() {
        });
        assertThat(offers2).hasSize(2);



//        11. user tries GET /offers/1234 (id 1234 does not exist) and gets 404(NOT FOUND) with message "Offer with id 1234 is not found"
        //given
        String id = "1234";
        //when
        ResultActions performGetOfferById = mockMvc.perform(get("/offers/" + id));
        //then
        performGetOfferById.andExpect(status().isNotFound())
                .andExpect(content().json("""
                                            {
                                                "message" : "Offer with id 1234 is not found",
                                                "status" : "NOT_FOUND"
                                            }
                                            """.trim()));
//        12. user tries GET/offers/1 and gets 200(OK) and an offer with id 1
        //given
        SaveOfferResultDto expectedOffer = offers2.get(0);
        String idFirstOffer = offers2.get(0).id();
        //when
        ResultActions performGetOfferWithIdOne = mockMvc.perform(get("/offers/" + idFirstOffer));
        //then
        MvcResult mvcResultOfferWithIdOne = performGetOfferWithIdOne.andExpect(status().isOk()).andReturn();
        String jsonOfferWithIdOne = mvcResultOfferWithIdOne.getResponse().getContentAsString();
        SaveOfferResultDto offerWithIdOne = objectMapper.readValue(jsonOfferWithIdOne, new TypeReference<>() {
        });
        assertAll(
                ()->offerWithIdOne.id().equals(expectedOffer.id()),
                ()->offerWithIdOne.offerUrl().equals(expectedOffer.offerUrl()),
                ()->offerWithIdOne.companyName().equals(expectedOffer.companyName()),
                ()->offerWithIdOne.jobPosition().equals(expectedOffer.jobPosition()),
                ()->offerWithIdOne.earnings().equals(expectedOffer.earnings())
        );
        //13.there are two offers in external http server
        //given&when&then

        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithNextTwoOffers())));

        //14.scheduler ran 2nd time and made GET to external server and system added two new offers with ids 3, 4 to DB
        //given&when
        List<SaveOfferResultDto> fetchedOffers3 = offerScheduler.fetchAllOffersAndSaveIfNotExist();
        //then
        assertThat(fetchedOffers3).hasSize(2);


        //15 user tries GET /offers with token and gets 200(OK) with 2 offers with ids 1, 2, 3, 4
        //given&when
        ResultActions performGetAllOffers3 = mockMvc.perform(get("/offers"));
        //then
        MvcResult mvcResult3 = performGetAllOffers3.andExpect(status().isOk()).andReturn();
        String jsonResult3 = mvcResult3.getResponse().getContentAsString();
        List<SaveOfferResultDto> offers3 = objectMapper.readValue(jsonResult3, new TypeReference<>() {
        });
        assertThat(offers3).hasSize(4);


        //16. user tries POST/offers and gets 201(CREATED)
        //given&when
        ResultActions performAddOffer = mockMvc.perform(post("/offers")
                .content("""
                            {
                                "offerUrl" : "dupa.pl",
                                "jobPosition" : "koparkowy",
                                "companyName": "gugl",
                                "earnings": "4000"
                            }
                            """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        String json = performAddOffer.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        SaveOfferResultDto parsedCreatedOfferJson = objectMapper.readValue(json, SaveOfferResultDto.class);
        String idParsedOfferJson = parsedCreatedOfferJson.id();
        assertAll(
                ()-> assertThat(parsedCreatedOfferJson.offerUrl()).isEqualTo("dupa.pl"),
                ()-> assertThat(parsedCreatedOfferJson.companyName()).isEqualTo("gugl"),
                ()-> assertThat(parsedCreatedOfferJson.earnings()).isEqualTo("4000"),
                ()-> assertThat(parsedCreatedOfferJson.jobPosition()).isEqualTo("koparkowy"),
                ()-> assertThat(idParsedOfferJson).isNotNull()
        );
        //17. user tries GET/offers and gets 200(OK) with 5 offers
        //given
        ResultActions performGetOneOffer = mockMvc.perform(get("/offers"));
        //when
        String jsonOneOffer = performGetOneOffer.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        //then

        List<SaveOfferResultDto> parsedOneOfferFromJson= objectMapper.readValue(jsonOneOffer, new TypeReference<>(){});

        assertAll(
                ()-> assertThat(parsedOneOfferFromJson).hasSize(5),
                ()-> assertThat(parsedOneOfferFromJson.stream().map(SaveOfferResultDto::id)).contains(idParsedOfferJson));
    }
}
