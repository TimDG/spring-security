package be.continuum.springsecurity.lostpass;

public class InvalidTokenException extends RuntimeException {

    InvalidTokenException(String token) {
        super("Token '" + token + "' not found.");
    }
}
