package be.continuum.springsecurity.config;

import be.continuum.springsecurity.lostpass.ExpiredTokenException;
import be.continuum.springsecurity.lostpass.InvalidTokenException;
import be.continuum.springsecurity.post.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundAdvice {

    @ExceptionHandler({PostNotFoundException.class, InvalidTokenException.class})
    public ResponseEntity<String> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({ExpiredTokenException.class})
    public String expiredToken() {
        return "component/expiredToken.html";
    }
}
