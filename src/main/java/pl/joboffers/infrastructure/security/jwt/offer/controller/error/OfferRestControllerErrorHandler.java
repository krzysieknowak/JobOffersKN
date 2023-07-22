package pl.joboffers.infrastructure.security.jwt.offer.controller.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.joboffers.domain.offer.OfferNotFoundException;

import java.util.Collections;

@ControllerAdvice
@Log4j2
class OfferRestControllerErrorHandler {
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public OfferRestControllerNotFoundErrorResponse notFoundErrorHandler(OfferNotFoundException exception){
        String message = exception.getMessage();
        log.error(message);
        return new OfferRestControllerNotFoundErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public OfferPostErrorConflictResponse errorConflictOfferAlreadyExists(DuplicateKeyException exception){
        final String message = "This url already exists";
        log.error(message);
        return new OfferPostErrorConflictResponse(Collections.singletonList(message), HttpStatus.CONFLICT);
    }
}
