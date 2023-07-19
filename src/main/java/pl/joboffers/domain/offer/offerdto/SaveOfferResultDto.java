package pl.joboffers.domain.offer.offerdto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record SaveOfferResultDto(
        String id,
        String offerUrl,
        String jobPosition,
        String companyName,
        String earnings) {
}
