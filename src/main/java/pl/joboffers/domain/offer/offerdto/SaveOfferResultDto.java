package pl.joboffers.domain.offer.offerdto;

import lombok.Builder;

@Builder
public record SaveOfferResultDto(String id,
                                 String companyName,
                                 String jobPosition,
                                 String earnings,
                                 String offerUrl) {
}
