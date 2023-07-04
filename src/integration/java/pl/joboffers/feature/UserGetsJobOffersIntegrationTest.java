package pl.joboffers.feature;


import org.junit.jupiter.api.Test;
import pl.joboffers.BaseIntegrationTest;

public class UserGetsJobOffersIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_user_log_in_and_get_all_available_job_offers(){
        //        1. there are no offers in external HTTP server
//        2. scheduler ran 1st time and made GET to external server and system added 0 offers to database
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
