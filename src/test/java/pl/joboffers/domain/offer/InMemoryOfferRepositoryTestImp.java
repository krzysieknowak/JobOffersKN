package pl.joboffers.domain.offer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOfferRepositoryTestImp implements OfferRepository {
    Map<String, Offer> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public Offer save(Offer offer) {
        UUID generatedID = UUID.randomUUID();

        return inMemoryDatabase.put(offer.id(), offer);
    }
}
