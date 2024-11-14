package com.hh99.ecommerce.product.interfaces.controller;

import com.hh99.ecommerce.product.interfaces.request.PopularProductRequest;
import com.hh99.ecommerce.product.interfaces.request.ProductRequest;
import com.hh99.ecommerce.product.interfaces.response.PopularProductResponse;
import com.hh99.ecommerce.product.interfaces.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "상품 관리 API")
public interface SwaggerProductController {
    @Operation(summary = "상품 등록")
    void addProduct(ProductRequest productRequest);

    @Operation(summary = "전체 상품 조회")
    List<ProductResponse> getProducts();

    @Operation(summary = "특정 상품 조회")
    ProductResponse getProduct(Long id);

    @Operation(summary = "인기 판매 상품 조회 API")
    List<PopularProductResponse> getPopularProducts(int topNumber, int lastDays);
}
