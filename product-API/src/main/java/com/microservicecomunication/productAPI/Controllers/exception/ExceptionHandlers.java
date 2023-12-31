package com.microservicecomunication.productAPI.Controllers.exception;

import com.microservicecomunication.productAPI.exception.AuthorizationException;
import com.microservicecomunication.productAPI.exception.ExceptionDetails;
import com.microservicecomunication.productAPI.exception.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ExceptionDetails> handleValidateException(ValidateException validateException){
        var details = new ExceptionDetails();
        details.setStatus(HttpStatus.BAD_REQUEST.value());
        details.setMessage(validateException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(details);
    }
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ExceptionDetails> handlerAuthorizationException(AuthorizationException authorizationException){
        var details = new ExceptionDetails();
        details.setMessage(authorizationException.getMessage());
        details.setStatus(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(details);
    }
}
