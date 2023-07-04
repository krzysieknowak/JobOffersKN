package pl.joboffers.domain.loginandregister;

public class UsernameFoundException extends RuntimeException{
    public UsernameFoundException(String message) {
        super(message);
    }
}
