package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class OfferFacadeConfiguration {

    @Bean
    OfferFacade offerFacade(OfferRepository offerRepository, OfferFetchable offerFetchable) {
        OfferService offerService = new OfferService(offerRepository, offerFetchable);
        return new OfferFacade(offerRepository, offerService);
    }
}
