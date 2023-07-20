package pl.joboffers.domain.offer.offerdto;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
public record SaveOfferRequestDto(
        @NotNull(message = "{offerUrl.not.null}")
        @NotEmpty(message = "{offerUrl.not.empty}")
        String offerUrl,
        @NotNull(message = "{jobPosition.not.null}")
        @NotEmpty(message = "{jobPosition.not.empty}")
        String jobPosition,
        @NotNull(message = "{companyName.not.null}")
        @NotEmpty(message = "{companyName.not.empty}")
        String companyName,
        @NotNull(message = "{earnings.not.null}")
        @NotEmpty(message = "{earnings.not.empty}")
        String earnings )
{
}
