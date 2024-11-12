package com.hh99.ecommerce.product.application.usecase;

import com.hh99.ecommerce.product.application.model.PopularProductInfo;
import com.hh99.ecommerce.product.domain.ProductService;
import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductService productService;

    public List<PopularProductInfo> getPopularProducts(Integer topNumber, Integer lastDays) {

        LocalDateTime startDateTime = LocalDateTime.now().minusDays(lastDays).with(LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.now().minusDays(1).with(LocalTime.MAX);
        List<ProductDomain> popularProducts = productService.getPopularProducts(topNumber, startDateTime, endDateTime);

        return popularProducts.stream()
                .map(productDomain -> PopularProductInfo.builder()
                        .rank(popularProducts.indexOf(productDomain) + 1)
                        .productInfo(productDomain.toProductInfo())
                        .build())
                .toList();
    }
}
