package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.offerdto.JobOfferResponseDto;

import java.util.List;

public class OfferFacadeTestConfiguration {

    private final InMemoryFetchTestImp fetchTestImp;
    private final InMemoryOfferRepository offerRepository;

    public OfferFacadeTestConfiguration() {
        this.fetchTestImp = new InMemoryFetchTestImp(
                List.of(
                        new JobOfferResponseDto("cleaner","google", "6000","url1"),
                        new JobOfferResponseDto("assistant","amazon", "3000","url2"),
                        new JobOfferResponseDto("tester","allegro", "8000","url3"),
                        new JobOfferResponseDto("chef","ebay", "8000","url4"),
                        new JobOfferResponseDto("devops","google", "7000","url5"),
                        new JobOfferResponseDto("tester","ibm", "4000","url6")
                ));
        this.offerRepository = new InMemoryOfferRepository();
    }
    public OfferFacadeTestConfiguration(List<JobOfferResponseDto> fetchedJobOffers){
        this.fetchTestImp = new InMemoryFetchTestImp(fetchedJobOffers);
        this.offerRepository = new InMemoryOfferRepository();
    }
    OfferFacade offerFacadeForTests(){
        return new OfferFacade(new InMemoryOfferRepository(), new OfferService(offerRepository, fetchTestImp));
    }
}
