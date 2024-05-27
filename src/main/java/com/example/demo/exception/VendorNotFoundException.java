package com.example.demo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VendorNotFoundException extends RuntimeException {

    public VendorNotFoundException(String message) {
        super(message);
    }
}
