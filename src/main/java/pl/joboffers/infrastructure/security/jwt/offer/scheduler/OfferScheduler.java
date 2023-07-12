package pl.joboffers.infrastructure.security.jwt.offer.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class OfferScheduler {

    private final OfferFacade offerFacade;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedDelayString = "${http.offers.scheduler.request.delay}")
    public List<SaveOfferResultDto> fetchAllOffersAndSaveIfNotExist(){
        log.info("Started fetching offers at {}", dateFormat.format(new Date()));
        List<SaveOfferResultDto> offers = offerFacade.fetchAllOffersAndSaveIfNotExist();
        log.info("Fetched offers: {}", offers.size());
        log.info("Finished fetching offers at {}", dateFormat.format(new Date()));
        return offers;
    }
}
