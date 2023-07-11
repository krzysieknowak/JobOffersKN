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
    OfferFacade offerFacade(OfferFetchable offerFetchable) {
        OfferRepository offerRepository = new OfferRepository() {
            @Override
            public Offer save(Offer offer) {
                return null;
            }

            @Override
            public Optional<Offer> findById(String id) {
                return Optional.empty();
            }

            @Override
            public List<Offer> findAllOffers() {
                return null;
            }

            @Override
            public boolean existsByOfferUrl(String offerUrlToCheck) {
                return false;
            }

            @Override
            public List<Offer> saveAll(List<Offer> filteredJobOffers) {
                return null;
            }
        };
        OfferService offerService = new OfferService(offerRepository, offerFetchable);
        return new OfferFacade(offerRepository, offerService);
    }
}
