package com.hh99.ecommerce.cart.application;

import com.hh99.ecommerce.cart.domain.CartDomain;
import com.hh99.ecommerce.cart.domain.CartService;
import com.hh99.ecommerce.cart.interfaces.CartResponse;
import com.hh99.ecommerce.product.domain.ProductService;
import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartUseCase {
    private final CartService cartService;
    private final ProductService productService;

    public List<CartResponse> getCarts(Long userId) {
        List<CartDomain> cartDomains = cartService.getCarts(userId);
        return cartDomains.stream().map(
                cartDomain -> {
                    ProductDomain productDomain = productService.getProduct(cartDomain.getProductId());
                    return CartResponse.builder()
                            .id(cartDomain.getId())
                            .userId(cartDomain.getUserId())
                            .productId(cartDomain.getProductId())
                            .productName(productDomain.getName())
                            .productPrice(productDomain.getPrice())
                            .quantity(cartDomain.getQuantity())
                            .createdAt(cartDomain.getCreatedAt())
                            .build();
                }
        ).collect(Collectors.toList());
    }
}
