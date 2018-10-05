package com.primedice.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InsufficientDepositException extends Exception {
    public InsufficientDepositException(String message) {
        super(message);
    }
}
