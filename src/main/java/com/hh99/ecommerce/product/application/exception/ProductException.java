package com.hh99.ecommerce.product.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    public ProductException(HttpStatus status, ProductErrorCode errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = String.format("PRODUCT-%03d", errorCode.ordinal() + 1);
    }
}
