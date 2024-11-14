package com.hh99.ecommerce.balance.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BalanceNotEnoughException extends BalanceException{
    public BalanceNotEnoughException() {
        super(HttpStatus.BAD_REQUEST, BalanceErrorCode.BALANCE_NOT_ENOUGH, "사용 가능한 잔액이 부족합니다.");
    }
}
