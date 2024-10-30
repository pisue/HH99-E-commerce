package com.hh99.ecommerce.product.application.usecase;

import com.hh99.ecommerce.product.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductService productService;


}
