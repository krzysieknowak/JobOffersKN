package pl.joboffers.domain.offer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository {

    Offer save(Offer offer);

    Optional<Offer> findById(String id);

    List<Offer> findAllOffers();

    boolean existsByOfferUrl(String offerUrlToCheck);

    List<Offer> saveAll(List<Offer> filteredJobOffers);
}
