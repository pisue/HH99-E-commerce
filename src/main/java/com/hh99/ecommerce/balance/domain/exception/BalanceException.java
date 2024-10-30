package com.hh99.ecommerce.balance.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BalanceException extends RuntimeException{
    private final HttpStatus status;
    private final String errorCode;

    public BalanceException(HttpStatus status, BalanceErrorCode errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = String.format("BALANCE-%03d", errorCode.ordinal() + 1);
    }
}
