package pl.joboffers.domain.offer;

public class OfferNotFoundException extends RuntimeException{

    private final String offerId;
    public OfferNotFoundException(String offerId) {
        super(String.format("Offer with id %s is not found", offerId));
        this.offerId = offerId;
    }
}
