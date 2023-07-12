package pl.joboffers.features;


import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.TestOffersDto;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;
import pl.joboffers.infrastructure.security.jwt.offer.scheduler.OfferScheduler;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserGetsJobOffersIntegrationTest extends BaseIntegrationTest implements TestOffersDto {
    @Autowired
    OfferScheduler offerScheduler;

    @Test
    public void should_user_see_offers_but_have_to_be_logged_in_beforehand_and_external_server_should_have_some_offers(){
//        1. there are no offers in external HTTP server
//        given
        wireMockServer.stubFor(WireMock.get("/offers")
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", "application/json")
                                .withBody(bodyWithZeroOffers())));
        //when
        //then


//        2. scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //given&when
        List<SaveOfferResultDto> fetchedOffers = offerScheduler.fetchAllOffersAndSaveIfNotExist();
        //then
        assertThat(fetchedOffers).isEmpty();


//        3 user tries to get a JVT Token using GET /token and gets unauthorized(401)
//        4 user tries to see offers with no token using GET /offers and get unauthorized(401)
//        5 user creates account using  POST /register, providing login and password
//        6 user get token using POST /token providing login and password and system returned OK (200) and a token code
//        7 user made GET /offers with token and got returned 200 (OK) and 0 offers
//        8 there are two offers in external http server
//        9 scheduler ran 2nd time and made GET to external server and system added two new offers with ids 1, 2 to DB
//        10 user tries GET /offers with token and gets 200(OK) with 2 offers with ids 1, 2
//        11 user tries GET /offers/1234 and gets 404(NOT FOUND) with message "Offer with id 1234 is not found"
//        12. user tries GET/offers/1 and gets 200(OK) and an offer with id 1
    }
}
