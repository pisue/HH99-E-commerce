package com.hh99.ecommerce.order.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OutOfStockException extends OrderException {
    public OutOfStockException() {
        super(HttpStatus.CONFLICT, OrderErrorCode.OUT_OF_STOCK, "재고가 부족합니다.");
    }
}
