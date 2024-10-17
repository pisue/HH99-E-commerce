package com.hh99.ecommerce.product.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductNotFoundException extends ProductException{

    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, ProductErrorCode.PRODUCT_NOT_FOUND, "조회하신 상품을 찾을 수 없습니다.");
    }
}
