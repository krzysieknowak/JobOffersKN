package pl.joboffers.domain.offer;

import java.util.List;

public class OfferSavingException extends RuntimeException{
    private final List<String> jobOffers;

    public OfferSavingException(String message, List<Offer> jobOffers) {
        super(String.format("error " + message + jobOffers));
        this.jobOffers = jobOffers
                .stream()
                .map(Offer::offerUrl)
                .toList();
    }
}
