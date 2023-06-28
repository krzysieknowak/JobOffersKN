package pl.joboffers.domain.offer;

public class OfferNotFoundException extends RuntimeException{
    public OfferNotFoundException(String offerNotFoundMessage) {
        super(offerNotFoundMessage);
    }
}
