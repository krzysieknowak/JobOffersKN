package pl.joboffers.domain.offer.offerdto;

public record JobOfferResponseDto(
        String jobPosition,
        String companyName,
        String earnings,
        String offerUrl
) {
}
