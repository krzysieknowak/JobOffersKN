package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.offerdto.SaveOfferDto;
import pl.joboffers.domain.offer.offerdto.SavingOfferResultDto;

import java.security.SecureRandom;
import java.util.Random;

/*
 oferty w bazie nie mogą się powtarzać (decyduje url oferty)
 klient może ręcznie dodać ofertę pracy
 każda oferta pracy ma (link do oferty, nazwę stanowiska, nazwę firmy, zarobki (mogą być widełki))
 klient może pobrać jedną ofertę pracy poprzez unikalne Id
 */
@AllArgsConstructor
public class OfferFacade {
    private static final String OFFER_NOT_FOUND = "Offer not found";
    private final OfferRepository offerRepository;
    public SavingOfferResultDto saveOfferToDatabase(SaveOfferDto saveOfferDto){

        final Offer offer = Offer
                .builder()
                .offerUrl(saveOfferDto.offerUrl())
                .jobPosition(saveOfferDto.jobPosition())
                .companyName(saveOfferDto.companyName())
                .earnings(saveOfferDto.earnings())
                .build();
        Offer savedOffer = offerRepository.save(offer);
        return new SavingOfferResultDto(savedOffer.id(), savedOffer.offerUrl(), true);
    }
}
