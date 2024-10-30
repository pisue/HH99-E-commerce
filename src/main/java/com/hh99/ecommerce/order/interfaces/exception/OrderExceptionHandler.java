package com.hh99.ecommerce.order.interfaces.exception;

import com.hh99.ecommerce.balance.domain.exception.InvalidAmountException;
import com.hh99.ecommerce.common.response.ErrorResponse;
import com.hh99.ecommerce.order.domain.exception.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerOutOfStockException(OutOfStockException exception) {
        return new ErrorResponse(exception.getStatus(), exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(InvalidAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerInvalidAmountException(InvalidAmountException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getErrorCode(), exception.getMessage());
    }

}
