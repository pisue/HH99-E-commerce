package com.hh99.ecommerce.product.domain;

import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import com.hh99.ecommerce.product.interfaces.request.ProductRequest;
import com.hh99.ecommerce.product.domain.exception.ProductNotFoundException;
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

    @Transactional
    public ProductDomain getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(Product::toDomain)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public void updateProductStock(Long productId, int newStock) {
        productRepository.findById(productId)
                .map(product -> {
                    product.setStock(newStock);
                    return productRepository.save(product);
        })
        .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductDomain> getProducts() {
        return productRepository.findAll().stream()
                .map(Product::toDomain)
                .toList();
    }

    public void create(ProductDomain productDomain) {
        productRepository.save(productDomain.generateProduct());
    }

    public List<ProductDomain> getPopularProducts(Integer topNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return productRepository.findPopularProducts(topNumber, startDateTime, endDateTime)
                .stream()
                .map(Product::toDomain)
                .toList();
    }

}
