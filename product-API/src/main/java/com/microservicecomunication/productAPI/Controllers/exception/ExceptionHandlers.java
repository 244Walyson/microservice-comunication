package com.microservicecomunication.productAPI.Controllers.exception;

import com.microservicecomunication.productAPI.exception.ExceptionDetails;
import com.microservicecomunication.productAPI.exception.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handlevalidateException(ValidateException validateException){
        var details = new ExceptionDetails();
        details.setStatus(HttpStatus.BAD_REQUEST.value());
        details.setMessage(validateException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(details);
    }
}
