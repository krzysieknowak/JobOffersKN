package pl.joboffers.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;

public record OfferRestControllerNotFoundErrorResponse(String message, HttpStatus status) {
}
