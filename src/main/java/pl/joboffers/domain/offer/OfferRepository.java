package pl.joboffers.domain.offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    Offer save(Offer offer);

    Optional<Offer> findById(String id);

    List<Offer> findAllOffers();

    boolean existsByOfferUrl(String offerUrlToCheck);

    List<Offer> saveAll(List<Offer> filteredJobOffers);
}
