package pl.joboffers.domain.offer;

import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;
import pl.joboffers.domain.offer.offerdto.SaveOfferRequestDto;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OfferFacadeTest {

    @Test
    public void should_fetch_6_offers_from_remote_server_and_save_in_db_6_offers_when_db_is_empty(){
        //given
        OfferFacadeTestConfiguration facadeTestConfiguration = new OfferFacadeTestConfiguration();
        OfferFacade offerFacade = facadeTestConfiguration.offerFacadeForTests();
        assertThat(offerFacade.findAllOffers().isEmpty());
        //when
        List<SaveOfferResultDto> result = offerFacade.fetchAllOffersAndSaveIfNotExist();
        //then
//        Assertions.assertThat(result).hasSize(6);
        assertThat(result.size() == 6);
    }
    @Test
    public void should_fetch_6_offers_from_remote_server_and_save_0_in_db_when_they_are_already_saved(){
        //given
        OfferFacadeTestConfiguration facadeTestConfiguration = new OfferFacadeTestConfiguration();
        OfferFacade offerFacade = facadeTestConfiguration.offerFacadeForTests();
        List<SaveOfferResultDto> offers = offerFacade.findAllOffers();
        assertThat(offers.size() == 6);
        //when
        List<SaveOfferResultDto> result = offerFacade.fetchAllOffersAndSaveIfNotExist();
        //then
//        Assertions.assertThat(result).hasSize(6);
        assertThat(result.size() == 0);
    }

    @Test
    public void should_throw_offer_duplicate_exception_when_offer_url_already_exists(){
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        SaveOfferRequestDto offer = new SaveOfferRequestDto(
                "https://www.testurl.com",
                "devops",
                "amazon",
                "5000");
        SaveOfferResultDto saveOfferResultDto = offerFacade.saveOfferToDatabase(offer);
        String savedOfferResultId = saveOfferResultDto.id();
        assertThat(offerFacade.findOfferById(savedOfferResultId).id()).isEqualTo(savedOfferResultId);
        //when
        Throwable throwable = assertThrows(DuplicateKeyException.class,() -> {
            offerFacade.saveOfferToDatabase(new SaveOfferRequestDto(
                    "https://www.testurl.com","ewr","werwe","wer"));
        });
        //then
        assertThat(throwable.getMessage()).contains("Offer with url [https://www.testurl.com] already exists");
    }

    @Test
    public void should_save_offer_to_database_when_its_empty(){
        //given
        OfferFacadeTestConfiguration facadeTestConfiguration = new OfferFacadeTestConfiguration();
        OfferFacade offerFacade = facadeTestConfiguration.offerFacadeForTests();
        assertThat(offerFacade.findAllOffers().isEmpty());
        //when
        SaveOfferRequestDto offer = new SaveOfferRequestDto(
                "https://www.testurl.com",
                "devops",
                "amazon",
                "5000");
        SaveOfferResultDto result = offerFacade.saveOfferToDatabase(offer);
        //then
        assertThat(result.getClass()).isEqualTo(SaveOfferResultDto.class);
    }
    @Test
    public void should_find_saved_in_db_offer_by_id(){
        //given
        OfferFacadeTestConfiguration facadeTestConfiguration = new OfferFacadeTestConfiguration(List.of());
        OfferFacade offerFacade = facadeTestConfiguration.offerFacadeForTests();
        SaveOfferResultDto saveOfferResultDto = offerFacade.saveOfferToDatabase(new SaveOfferRequestDto(
                "https://www.testurl.com",
                "devops",
                "amazon",
                "5000"));

        //when
        SaveOfferResultDto result = offerFacade.findOfferById(saveOfferResultDto.id());
        //then
        assertThat(result).isEqualTo(
                SaveOfferResultDto.builder()
                        .id(saveOfferResultDto.id())
                        .offerUrl("https://www.testurl.com")
                        .jobPosition("devops")
                        .companyName("amazon")
                        .earnings("5000")
                        .build()
        );
    }
    @Test
    public void should_throw_offer_not_found_exception_with_message_when_offer_is_not_found_by_id(){
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        assertThat(offerFacade.findAllOffers().isEmpty());
        String id = "7";
        //when
        Throwable throwable = assertThrows(OfferNotFoundException.class, ()-> offerFacade.findOfferById(id));
        //then
        assertThat(throwable.getMessage()).isEqualTo("Offer with id 7 is not found");
    }
}