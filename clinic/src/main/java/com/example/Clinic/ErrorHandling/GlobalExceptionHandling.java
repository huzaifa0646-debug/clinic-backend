package com.example.Clinic.ErrorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<String> handleArithmeticException(ArithmeticException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return new ResponseEntity<>("A null value was encountered: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    // Catch-all for your custom service exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalStateException.class)
public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
}


@ExceptionHandler(HttpMessageNotReadableException.class)
public ResponseEntity<String> handleMissingBody(HttpMessageNotReadableException e) {
    return new ResponseEntity<>("Request body is missing or malformed", HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(UsernameNotFoundException.class) 
public ResponseEntity<String> handleUserNotFound(UsernameNotFoundException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
}

}