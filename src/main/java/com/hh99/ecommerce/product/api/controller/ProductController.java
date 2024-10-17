package com.hh99.ecommerce.product.api.controller;

import com.hh99.ecommerce.product.api.request.ProductRequest;
import com.hh99.ecommerce.product.api.response.ProductResponse;
import com.hh99.ecommerce.product.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController implements SwaggerProductController{

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody ProductRequest productRequest) {
        productService.create(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PatchMapping("/{id}/stock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductStock(@PathVariable Long id, @RequestParam int newStock) {
        productService.updateProductStock(id, newStock);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getPopularProducts() {
        List<ProductResponse> popularProductsResponse = new ArrayList<>();
        popularProductsResponse.add(new ProductResponse(4L, "상품 D", "D 상품입니다.", new BigDecimal(10000), 50, LocalDateTime.now()));
        popularProductsResponse.add(new ProductResponse(5L, "상품 E", "E 상품입니다.", new BigDecimal(20000), 100, LocalDateTime.now()));
        popularProductsResponse.add(new ProductResponse(6L, "상품 F", "F 상품입니다.", new BigDecimal(30000), 70, LocalDateTime.now()));

        return popularProductsResponse;
    }


}
