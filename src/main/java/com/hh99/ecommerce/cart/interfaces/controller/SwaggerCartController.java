package com.hh99.ecommerce.cart.interfaces.controller;

import com.hh99.ecommerce.cart.interfaces.CartRequest;
import com.hh99.ecommerce.cart.interfaces.CartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "장바구니 API")
public interface SwaggerCartController {

    @Operation(summary = "장바구니 추가")
    void add(CartRequest cartRequest);

    @Operation(summary = "장바구니 목록 조회")
    List<CartResponse> getCarts(@PathVariable Long userId);

    @Operation(summary = "장바구니에서 삭제")
    void remove(@PathVariable Long id);
}
