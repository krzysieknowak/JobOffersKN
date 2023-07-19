package pl.joboffers.domain.offer.offerdto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record SaveOfferRequestDto(
        @NotNull(message = "is empty")
        @NotNull(message = "id is null")
        String offerUrl,
        @NotNull(message = "is empty")
        @NotNull(message = "is null")
        String jobPosition,
        @NotNull(message = "is empty")
        @NotNull(message = "is null")
        String companyName,
        @NotNull(message = "earnings must not be empty")
        @NotNull(message = "earnings must not be null")
        String earnings )
{
}
