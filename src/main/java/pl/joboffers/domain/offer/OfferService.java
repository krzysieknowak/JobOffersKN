package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.joboffers.domain.offer.offerdto.JobOfferResponseDto;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class OfferService {
    private final OfferRepository offerRepository;
    private final OfferFetchable offerFetchable;
    public List<Offer> fetchAllOffersAndSaveIfNotExist() {
        List<Offer> allJobOffers = fetchOffers();
        final List<Offer> filteredJobOffers = filterNotExistingOffers(allJobOffers);
        return offerRepository.saveAll(filteredJobOffers);
    }


    private List<Offer> fetchOffers(){
        return offerFetchable.fetchOffers()
                .stream()
                .map(OfferMapper::mapFromJobOfferResponseDtoToOffer)
                .toList();
    }

    private List<Offer> filterNotExistingOffers(List<Offer> allJobOffers){
        return allJobOffers.stream()
                .filter(offer -> !offer.offerUrl().isEmpty())
                .filter(offer -> !offerRepository.existsByOfferUrl(offer.offerUrl()))
                .collect(Collectors.toList());
    }
}
