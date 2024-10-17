package com.hh99.ecommerce.product.api.exception;

import com.hh99.ecommerce.balance.application.exception.UserNotFoundException;
import com.hh99.ecommerce.common.response.ErrorResponse;
import com.hh99.ecommerce.product.application.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerProductNotFoundException(ProductNotFoundException exception) {
        return new ErrorResponse(exception.getStatus(), exception.getErrorCode(), exception.getMessage());
    }
}
