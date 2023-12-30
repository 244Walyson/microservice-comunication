package com.microservicecomunication.productAPI.exception;

import lombok.Data;

@Data
public class ExceptionDetails {

    private int status;
    private String message;
}
