package pl.joboffers.domain.offer;

import lombok.Builder;

@Builder
record Offer(String id, String offerUrl, String jobPosition, String companyName, String earnings) {
}
