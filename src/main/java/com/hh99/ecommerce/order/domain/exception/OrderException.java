package com.hh99.ecommerce.order.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderException extends RuntimeException{
    private final HttpStatus status;
    private final String errorCode;

    public OrderException(HttpStatus status, OrderErrorCode errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = String.format("ORDER-%03d", errorCode.ordinal() + 1);
    }
}
