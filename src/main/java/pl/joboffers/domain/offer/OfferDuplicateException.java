package pl.joboffers.domain.offer;


import java.util.List;

public class OfferDuplicateException extends RuntimeException {
    private final List<String> offerUrls;
    public OfferDuplicateException(String offerUrl) {
        super(String.format("Offer with URL [%s] already exists", offerUrl));
        this.offerUrls = List.of(offerUrl);
    }
}
