package pl.joboffers.domain.offer;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import pl.joboffers.domain.offer.offerdto.SaveOfferDto;
import pl.joboffers.domain.offer.offerdto.SavingOfferResultDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OfferFacadeTest {
    OfferFacade offerFacade = new OfferFacade(new InMemoryOfferRepositoryTestImp());

    @Test
    public void should_save_offer_to_database(){
        //given
        SaveOfferDto saveOfferDto = new SaveOfferDto("testurl.pl","devops", "gogole", "5000");
        //when
        SavingOfferResultDto resultSaving = offerFacade.saveOfferToDatabase(saveOfferDto);
        //then
        assertAll(
                ()-> Assertions.assertThat(resultSaving.isSaved()).isTrue(),
                ()-> Assertions.assertThat(resultSaving.offerUrl()).isEqualTo("testurl.pl"));

    }
}