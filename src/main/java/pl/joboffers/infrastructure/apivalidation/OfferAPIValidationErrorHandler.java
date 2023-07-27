package pl.joboffers.infrastructure.apivalidation;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class OfferAPIValidationErrorHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public OfferAPIValidationErrorResponse handleValidationExceptions(MethodArgumentNotValidException e){
        List<String> errors = getErrors(e);
        log.error(errors);
        return new OfferAPIValidationErrorResponse(errors, HttpStatus.BAD_REQUEST);
    }

    public List<String> getErrors(MethodArgumentNotValidException e){
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
