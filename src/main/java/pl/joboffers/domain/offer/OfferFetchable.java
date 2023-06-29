package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.offerdto.JobOfferResponseDto;

import java.util.List;

public interface OfferFetchable {

    List<JobOfferResponseDto> fetchOffers();
}
