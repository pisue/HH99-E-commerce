package com.hh99.ecommerce.balance.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserBalanceNotFoundException extends BalanceException{
    public UserBalanceNotFoundException() {
        super(HttpStatus.NOT_FOUND, BalanceErrorCode.BALANCE_NOT_FOUND, "사용자의 잔액정보를 찾을 수 없습니다.");
    }
}
