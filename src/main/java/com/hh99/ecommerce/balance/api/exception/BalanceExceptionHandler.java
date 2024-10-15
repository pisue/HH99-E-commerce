package com.hh99.ecommerce.balance.api.exception;

import com.hh99.ecommerce.balance.application.exception.InvalidAmountException;
import com.hh99.ecommerce.balance.application.exception.UserNotFoundException;
import com.hh99.ecommerce.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BalanceExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerUserNotFoundException(UserNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(InvalidAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerInvalidAmountException(InvalidAmountException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getErrorCode(), exception.getMessage());
    }

}
