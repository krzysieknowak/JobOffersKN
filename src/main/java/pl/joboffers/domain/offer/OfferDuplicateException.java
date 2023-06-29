package pl.joboffers.domain.offer;

public class OfferDuplicateException extends RuntimeException {

    public OfferDuplicateException(String offerUrl) {
        super(String.format("Offer with URL [%s] already exists", offerUrl));
    }
}
