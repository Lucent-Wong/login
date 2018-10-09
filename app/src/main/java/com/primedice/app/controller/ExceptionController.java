package com.primedice.app.controller;

import com.primedice.common.entity.ErrorResult;
import com.primedice.common.exceptions.InsufficientBalanceException;
import com.primedice.common.exceptions.InsufficientDepositException;
import com.primedice.common.exceptions.InternalErrorException;
import com.primedice.common.exceptions.InvalidValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResult exceptionHandler(Exception e) {
        log.error("internal error", e);
        return ErrorResult.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage("Internal error").build();
    }

    @ExceptionHandler(value = InternalErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResult internalError(Exception e) {
        log.error("internal error", e);
        return ErrorResult.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage("Internal error").build();
    }

    @ExceptionHandler(value = InsufficientBalanceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResult insufficientBalance(Exception e) {
        log.error("insufficient balance", e);
        return ErrorResult.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(e.getMessage()).build();
    }

    @ExceptionHandler(value = InsufficientDepositException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResult insufficientDeposit(Exception e) {
        log.error("insufficient deposit", e);
        return ErrorResult.builder().errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(e.getMessage()).build();
    }

    @ExceptionHandler(value = InvalidValueException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResult invalidValue(Exception e) {
        log.error("invalidValue", e);
        return ErrorResult.builder().errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(e.getMessage()).build();
    }
}