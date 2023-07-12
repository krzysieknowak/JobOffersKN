package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.offerdto.JobOfferResponseDto;
import pl.joboffers.domain.offer.offerdto.SaveOfferRequestDto;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;

public class OfferMapper {


    public static Offer mapFromSavingOfferDtoToOffer(SaveOfferRequestDto savingOfferDto){
        return Offer.builder()
                .offerUrl(savingOfferDto.offerUrl())
                .jobPosition(savingOfferDto.jobPosition())
                .companyName(savingOfferDto.companyName())
                .earnings(savingOfferDto.earnings())
                .build();
    }

    public static SaveOfferResultDto mapFromOfferToSaveOfferDto(Offer savedOffer) {
        return SaveOfferResultDto.builder()
                .id(savedOffer.id())
                .companyName(savedOffer.companyName())
                .jobPosition(savedOffer.jobPosition())
                .earnings(savedOffer.earnings())
                .offerUrl(savedOffer.offerUrl())
                .build();
    }

    public static Offer mapFromJobOfferResponseDtoToOffer(JobOfferResponseDto jobOfferResponseDto) {
        return Offer
                .builder()
                .jobPosition(jobOfferResponseDto.title())
                .companyName(jobOfferResponseDto.company())
                .earnings(jobOfferResponseDto.salary())
                .offerUrl(jobOfferResponseDto.offerUrl())
                .build();
    }
}
