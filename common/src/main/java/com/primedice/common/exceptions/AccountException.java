package com.primedice.common.exceptions;

public abstract class AccountException extends Exception {
    public AccountException(String message) {
        super(message);
    }
}
