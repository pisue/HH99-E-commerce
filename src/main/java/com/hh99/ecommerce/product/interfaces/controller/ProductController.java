package com.hh99.ecommerce.product.interfaces.controller;

import com.hh99.ecommerce.product.application.model.PopularProductInfo;
import com.hh99.ecommerce.product.application.usecase.ProductUseCase;
import com.hh99.ecommerce.product.domain.ProductService;
import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import com.hh99.ecommerce.product.interfaces.request.PopularProductRequest;
import com.hh99.ecommerce.product.interfaces.request.ProductRequest;
import com.hh99.ecommerce.product.interfaces.response.PopularProductResponse;
import com.hh99.ecommerce.product.interfaces.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController implements SwaggerProductController{
    private final ProductService productService;
    private final ProductUseCase productUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody ProductRequest request) {
        productService.create(ProductDomain.builder()
                .name(request.getName())
                .description(request.getDescription())
                .stock(request.getStock())
                .price(request.getPrice())
                .build());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts() {
        List<ProductDomain> productDomains = productService.getProducts();
        return productDomains.stream().map(
                productDomain -> ProductResponse.builder()
                        .id(productDomain.getId())
                        .name(productDomain.getName())
                        .description(productDomain.getDescription())
                        .price(productDomain.getPrice())
                        .stock(productDomain.getStock())
                        .build()
        ).collect(Collectors.toList());
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

/*    @PatchMapping("/stock/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductStock(@PathVariable Long id, @RequestParam int newStock) {
        productService.deductProductStock(id, newStock);
    }*/

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<PopularProductResponse> getPopularProducts(
            @RequestParam int topNumber,
            @RequestParam int lastDays) {
        return productUseCase.getPopularProducts(topNumber, lastDays).stream()
                .map(PopularProductInfo::toResponse)
                .toList();
    }



}
