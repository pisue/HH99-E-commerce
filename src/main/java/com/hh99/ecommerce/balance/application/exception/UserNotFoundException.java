package com.hh99.ecommerce.balance.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends BalanceException{
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, BalanceErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.");
    }
}
