package pl.joboffers.controller.error;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferDuplicateUrlErrorIntegrationTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void should_return_409_conflict_error_when_user_adds_offer_with_url_that_already_exists() throws Exception {
        //step 1
        //Adding offer with url that is not already in database and getting 201 (CREATED) and
        //checking if offer is added saved correctly
        //given&when
        ResultActions performAddGivenOfferFirstTime = mockMvc.perform(post("/offers")
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
        String jsonContentAsString = performAddGivenOfferFirstTime.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        SaveOfferResultDto savedOffer = objectMapper.readValue(jsonContentAsString, new TypeReference<>() {
        });
        assertAll(
                ()-> assertThat(savedOffer.offerUrl()).isEqualTo("dupa.pl"),
                ()-> assertThat(savedOffer.companyName()).isEqualTo("gugl"),
                ()-> assertThat(savedOffer.earnings()).isEqualTo("4000"),
                ()-> assertThat(savedOffer.jobPosition()).isEqualTo("koparkowy"),
                ()-> assertThat(savedOffer.id()).isNotNull()
                );

        //step 2
        //Adding offer with url that already exists in database(Added above) and getting 409(CONFLICT)
        //and checking if message is correct
        //given&when
        ResultActions performAddGivenOfferSecondTime = mockMvc.perform(post("/offers")
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
        performAddGivenOfferSecondTime.andExpect(status().isConflict())
                .andExpect(content().json("""
                                    {
                                        "messages": [
                                            "This url already exists"
                                        ],
                                        "status": "CONFLICT"
                                    }
                                   """.trim()
                ));


    }
}
