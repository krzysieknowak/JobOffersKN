package pl.joboffers.infrastructure.security.jwt.apivalidation;

import org.springframework.http.HttpStatus;

import java.util.List;

public record OfferAPIValidationErrorResponse(List<String> errorList, HttpStatus status) {
}
