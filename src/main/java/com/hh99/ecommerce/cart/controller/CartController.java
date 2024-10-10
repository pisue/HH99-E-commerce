package com.hh99.ecommerce.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    /*@GetMapping("{userId}")
    @Operation(summary = "장바구니 목록 조회")
    public ResponseEntity<List<CartResponse>> getCarts(@PathVariable Long userId) {

    }*/

}
