package com.hh99.ecommerce.cart.domain.exception;

import org.springframework.http.HttpStatus;

public class CartException extends RuntimeException{
    private final HttpStatus status;
    private final String errorCode;

    public CartException(HttpStatus status, CartErrorCode errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = String.format("CART-%03d", errorCode.ordinal() + 1);
    }
}
