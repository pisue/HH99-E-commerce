package com.hh99.ecommerce.product.application.service;

import com.hh99.ecommerce.product.interfaces.request.ProductRequest;
import com.hh99.ecommerce.product.interfaces.response.ProductResponse;
import com.hh99.ecommerce.product.application.exception.ProductNotFoundException;
import com.hh99.ecommerce.product.infra.Product;
import com.hh99.ecommerce.product.infra.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Cacheable(value = "products", key = "#productId")
    @Transactional
    public ProductResponse getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(product -> ProductResponse.builder()
                        .id(productId)
                        .stock(product.getStock())
                        .name(product.getName())
                        .price(product.getPrice())
                        .regDate(product.getRegDate())
                        .description(product.getDescription())
                        .build())
                .orElseThrow(ProductNotFoundException::new);
    }

    @CacheEvict(value = "products", key = "#productId")
    @Transactional
    public void updateProductStock(Long productId, int newStock) {
        productRepository.findById(productId)
                .map(product -> {
                    product.setStock(newStock);
                    return productRepository.save(product);
        })
        .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .stock(product.getStock())
                        .name(product.getName())
                        .price(product.getPrice())
                        .regDate(product.getRegDate())
                        .description(product.getDescription())
                        .build()
                ).collect(Collectors.toList());
    }

    public void create(ProductRequest productRequest) {
        productRepository.save(Product.builder()
                .id(productRequest.getId())
                .stock(productRequest.getStock())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .regDate(LocalDateTime.now())
                .description(productRequest.getDescription())
                .build());
    }
}
