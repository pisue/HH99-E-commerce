package com.hh99.ecommerce.product.domain.exception;

import com.hh99.ecommerce.order.domain.exception.OrderErrorCode;
import com.hh99.ecommerce.order.domain.exception.OrderException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OutOfStockException extends ProductException {
    public OutOfStockException() {
        super(HttpStatus.CONFLICT, ProductErrorCode.OUT_OF_STOCK, "재고가 부족합니다.");
    }
}
