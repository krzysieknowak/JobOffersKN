package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import pl.joboffers.domain.offer.offerdto.SaveOfferRequestDto;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;

import java.util.List;
import java.util.stream.Collectors;

/*
 *REQUIREMENTS*
 oferty w bazie nie mogą się powtarzać (decyduje url oferty)
 klient może ręcznie dodać ofertę pracy
 każda oferta pracy ma (link do oferty, nazwę stanowiska, nazwę firmy, zarobki (mogą być widełki))
 klient może pobrać jedną ofertę pracy poprzez unikalne Id
 */

@AllArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public List<SaveOfferResultDto> fetchAllOffersAndSaveIfNotExist() {
        return offerService.fetchAllOffersAndSaveIfNotExist()
                .stream()
                .map(OfferMapper::mapFromOfferToSaveOfferDto)
                .toList();
    }
    @Cacheable(value = "jobOffers")
    public List<SaveOfferResultDto> findAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(OfferMapper::mapFromOfferToSaveOfferDto)
                .collect(Collectors.toList());
    }

    public SaveOfferResultDto findOfferById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapFromOfferToSaveOfferDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    public SaveOfferResultDto saveOfferToDatabase(SaveOfferRequestDto saveOfferDto) {
        final Offer offerDocument = OfferMapper.mapFromSavingOfferDtoToOffer(saveOfferDto);
        Offer savedOffer = offerRepository.save(offerDocument);
        return OfferMapper.mapFromOfferToSaveOfferDto(savedOffer);
    }
}
