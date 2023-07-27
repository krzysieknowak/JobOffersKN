package pl.joboffers.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;

import java.util.List;

public record OfferPostErrorConflictResponse(List<String> messages,
                                             HttpStatus status) {
}
