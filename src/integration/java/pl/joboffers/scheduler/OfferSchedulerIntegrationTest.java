package pl.joboffers.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.JobOffersSpringBootApplication;
import pl.joboffers.domain.offer.OfferFetchable;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@SpringBootTest(classes = JobOffersSpringBootApplication.class, properties ="scheduling.enabled=true")
public class OfferSchedulerIntegrationTest extends BaseIntegrationTest {

    @SpyBean
    OfferFetchable offerFetcher;

    @Test
    public void should_perform_fetching_offers_2_times_in_2_secs(){
        await()
                .atMost(Duration.ofSeconds(2))
                .untilAsserted(()-> verify(offerFetcher, times(2)).fetchOffers());
    }
}
