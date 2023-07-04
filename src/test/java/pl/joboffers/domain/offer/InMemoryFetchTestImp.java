package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.offerdto.JobOfferResponseDto;

import java.util.List;

public class InMemoryFetchTestImp implements OfferFetchable{
    List<JobOfferResponseDto> jobOffers;

    public InMemoryFetchTestImp(List<JobOfferResponseDto> jobOffers) {
        this.jobOffers = jobOffers;
    }

    @Override
    public List<JobOfferResponseDto> fetchOffers() {
        return jobOffers;
    }
}
