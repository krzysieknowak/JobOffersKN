package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.offerdto.JobOfferResponseDto;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class OfferService {
    private final OfferRepository offerRepository;
    private final OfferFetchable offerFetchable;
    List<Offer> fetchAllOffersAndSaveIfNotExist() {
        List<Offer> allJobOffers = fetchOffers();
        List<Offer> filteredJobOffers = filterNotExistingOffers(allJobOffers);
        try{
            return offerRepository.saveAll(filteredJobOffers);
        } catch(OfferDuplicateException e ){
            throw new OfferSavingException(e.getMessage(), allJobOffers);
        }
    }

    private List<Offer> filterNotExistingOffers(List<Offer> allJobOffers){
        return allJobOffers.stream()
                .filter(offer -> !offer.offerUrl().isEmpty())
                .filter(offer -> !offerRepository.existsByOfferUrl(offer.offerUrl()))
                .collect(Collectors.toList());
    }

    private List<Offer> fetchOffers(){
        return offerFetchable.fetchOffers()
                .stream()
                .map(OfferMapper::mapFromJobOfferResponseDtoToOffer)
                .toList();
    }
}
