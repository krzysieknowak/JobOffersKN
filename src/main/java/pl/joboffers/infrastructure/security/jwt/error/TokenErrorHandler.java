package pl.joboffers.infrastructure.security.jwt.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class TokenErrorHandler {
    private final String BAD_CREDENTIALS = "Bad credentials";
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public TokenUnauthorizedErrorResponse f (BadCredentialsException exception){
        String message = exception.getMessage();
        log.error(message);
        return new TokenUnauthorizedErrorResponse(BAD_CREDENTIALS, HttpStatus.UNAUTHORIZED);
    }
}
