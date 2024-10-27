package com.hh99.ecommerce.cart.domain.exception;

import org.springframework.http.HttpStatus;

public class CartNotFoundException extends CartException {
    public CartNotFoundException() {
        super(HttpStatus.NOT_FOUND, CartErrorCode.CART_NOT_FOUND, "장바구니 정보를 찾을 수 없습니다.");
    }
}
