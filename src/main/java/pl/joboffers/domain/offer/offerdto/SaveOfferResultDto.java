package pl.joboffers.domain.offer.offerdto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record SaveOfferResultDto(
        String id,
        String offerUrl,
        String jobPosition,
        String companyName,
        String earnings) implements Serializable {
}
