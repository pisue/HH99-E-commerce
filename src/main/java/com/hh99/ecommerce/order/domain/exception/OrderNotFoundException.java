package com.hh99.ecommerce.order.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderNotFoundException extends OrderException {
    public OrderNotFoundException() {
        super(HttpStatus.NOT_FOUND, OrderErrorCode.ORDER_NOT_FOUND, "주문정보가 존재하지 않습니다.");
    }
}
