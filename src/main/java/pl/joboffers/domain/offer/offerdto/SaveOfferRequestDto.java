package pl.joboffers.domain.offer.offerdto;

import lombok.Builder;

@Builder
public record SaveOfferRequestDto(String offerUrl,
                                  String jobPosition,
                                  String companyName,
                                  String earnings ) {
}
