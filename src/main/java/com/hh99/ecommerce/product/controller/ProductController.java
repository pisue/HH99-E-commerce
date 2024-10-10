package com.hh99.ecommerce.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/api/products")
@Tag(name = "상품 관리 API")
public class ProductController {

    @PostMapping
    @Operation(summary = "상품 등록")
    public HttpStatus addProduct(@RequestBody ProductRequest productRequest) {
        return HttpStatus.OK;
    }

    @GetMapping
    @Operation(summary = "전체 상품 조회")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> productsResponse = new ArrayList<>();
        productsResponse.add(new ProductResponse(1L, "상품 A", "A 상품입니다.", new BigDecimal(10000), 50, LocalDateTime.now()));
        productsResponse.add(new ProductResponse(2L, "상품 B", "B 상품입니다.", new BigDecimal(20000), 100, LocalDateTime.now()));
        productsResponse.add(new ProductResponse(3L, "상품 C", "C 상품입니다.", new BigDecimal(30000), 70, LocalDateTime.now()));

        return ResponseEntity.ok(productsResponse);
    }

    @GetMapping("{id}")
    @Operation(summary = "특정 상품 조회")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(new ProductResponse(1L, "상품 A", "A 상품입니다.", new BigDecimal(10000), 50, LocalDateTime.now()));
    }

    @GetMapping("/popular")
    @Operation(summary = "인기 판매 상품 조회 API")
    public ResponseEntity<List<ProductResponse>> getPopularProducts() {
        List<ProductResponse> popularProductsResponse = new ArrayList<>();
        popularProductsResponse.add(new ProductResponse(4L, "상품 D", "D 상품입니다.", new BigDecimal(10000), 50, LocalDateTime.now()));
        popularProductsResponse.add(new ProductResponse(5L, "상품 E", "E 상품입니다.", new BigDecimal(20000), 100, LocalDateTime.now()));
        popularProductsResponse.add(new ProductResponse(6L, "상품 F", "F 상품입니다.", new BigDecimal(30000), 70, LocalDateTime.now()));

        return ResponseEntity.ok(popularProductsResponse);
    }


}
