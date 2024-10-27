package com.hh99.ecommerce.product.interfaces.controller;

import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import com.hh99.ecommerce.product.interfaces.request.ProductRequest;
import com.hh99.ecommerce.product.interfaces.response.ProductResponse;
import com.hh99.ecommerce.product.domain.ProductService;
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
        ProductDomain productDomain = productService.getProduct(id);
        return ProductResponse.builder()
                .id(productDomain.getId())
                .name(productDomain.getName())
                .description(productDomain.getDescription())
                .price(productDomain.getPrice())
                .stock(productDomain.getStock())
                .build();
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

        return popularProductsResponse;
    }


}
