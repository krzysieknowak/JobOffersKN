package pl.joboffers.domain.offer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOfferRepository implements OfferRepository {
    Map<String, Offer> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer entity) {
        if(inMemoryDatabase.values().stream().anyMatch(offer -> offer.offerUrl().equals(entity.offerUrl()))){
            throw new OfferDuplicateException(entity.offerUrl());
        }
        UUID generatedId = UUID.randomUUID();
        Offer offer = Offer
                .builder()
                .id(generatedId.toString())
                .companyName(entity.companyName())
                .jobPosition(entity.jobPosition())
                .earnings(entity.earnings())
                .offerUrl(entity.offerUrl())
                .build();
        inMemoryDatabase.put(generatedId.toString(), offer);
        return offer;
    }

    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public List<Offer> findAllOffers() {
        return inMemoryDatabase.values()
                .stream()
                .toList();
    }

    @Override
    public boolean existsByOfferUrl(String offerUrlToCheck) {
        return inMemoryDatabase.values()
                .stream()
                .anyMatch(offer -> offer.offerUrl().equals(offerUrlToCheck));
    }

    @Override
    public List<Offer> saveAll(List<Offer> filteredJobOffers) {
        List<Offer> savedOffers = new ArrayList<>();
        for(Offer offer : filteredJobOffers){
            Offer savedOffer = save(offer);
            savedOffers.add(savedOffer);
        }
        return savedOffers;
    }
}

