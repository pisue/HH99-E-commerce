package com.hh99.ecommerce.balance.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidAmountException extends BalanceException {
    public InvalidAmountException() {
        super(HttpStatus.BAD_REQUEST, BalanceErrorCode.INVALID_AMOUNT, "충전 금액은 양수여야 합니다.");
    }
}
