package com.primedice.common.exceptions;

import javax.security.auth.login.AccountException;

public class InsufficientDepositException extends AccountException {
    public InsufficientDepositException(String message) {
        super(message);
    }
}
