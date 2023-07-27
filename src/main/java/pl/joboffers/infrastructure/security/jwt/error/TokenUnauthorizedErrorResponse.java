package pl.joboffers.infrastructure.security.jwt.error;

import org.springframework.http.HttpStatus;

public record TokenUnauthorizedErrorResponse(String message, HttpStatus status) {
}
