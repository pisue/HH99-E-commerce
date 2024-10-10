package com.hh99.ecommerce.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/api/carts")
@Tag(name = "장바구니 API")
public class CartController {

    @PostMapping
    @Operation(summary = "장바구니 추가")
    public ResponseEntity<CartResponse> add(@RequestBody CartRequest cartRequest) {
        return ResponseEntity.ok(new CartResponse(1L, 1L, 2, LocalDateTime.now()));
    }

    @GetMapping("{userId}")
    @Operation(summary = "장바구니 목록 조회")
    public ResponseEntity<List<CartResponse>> getCarts(@PathVariable Long userId) {
        //TODO : productId로 상품정보 조회 후 Response에 담에서 보내기

        CartResponse cartResponse = new CartResponse(1L, 1L, 2, LocalDateTime.now());
        CartResponse cartResponse2 = new CartResponse(1L, 2L, 1, LocalDateTime.now());
        CartResponse cartResponse3 = new CartResponse(1L, 3L, 6, LocalDateTime.now());
        CartResponse cartResponse4 = new CartResponse(1L, 4L, 4, LocalDateTime.now());

        return ResponseEntity.ok(List.of(cartResponse, cartResponse2, cartResponse3, cartResponse4));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "장바구니에서 삭제")
    public ResponseEntity<Map<String, CartResponse>> delete(@PathVariable Long id) {

        CartResponse cartResponse = new CartResponse(1L, 1L, 2, LocalDateTime.now());
        return ResponseEntity.ok(Map.of("삭제되었습니다", cartResponse));
    }

}
