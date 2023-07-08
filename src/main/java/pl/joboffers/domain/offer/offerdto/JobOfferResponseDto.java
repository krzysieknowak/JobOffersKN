package pl.joboffers.domain.offer.offerdto;

import lombok.Builder;

@Builder
public record JobOfferResponseDto(
        String title,
        String company,
        String salary,
        String offerUrl
) {
}
