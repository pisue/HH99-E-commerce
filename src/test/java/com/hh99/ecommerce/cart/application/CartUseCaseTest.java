package com.hh99.ecommerce.cart.application;

import com.hh99.ecommerce.cart.domain.CartDomain;
import com.hh99.ecommerce.cart.domain.CartService;
import com.hh99.ecommerce.cart.interfaces.CartResponse;
import com.hh99.ecommerce.common.support.IntegrationTest;
import com.hh99.ecommerce.product.domain.ProductService;
import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CartUseCaseTest extends IntegrationTest {

    @Autowired
    private CartUseCase cartUseCase;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    private Long testUserId;

    @BeforeEach
    void setUp() {
        testUserId = 1L;
        
        // 테스트용 상품 생성
        productService.create(
                ProductDomain.builder()
                .name("테스트 상품")
                .description("테스트 상품 설명")
                .price(new BigDecimal(10000))
                .stock(100)
                .build()
        );
    }

    @Test
    @DisplayName("장바구니 목록 조회 - 성공")
    void getCarts_success() {
        // given

        CartDomain cartDomain = CartDomain.builder()
                .userId(testUserId)
                .productId(1L)
                .quantity(2)
                .createdAt(LocalDateTime.now())
                .build();

        cartService.add(cartDomain);

        // when
        List<CartResponse> result = cartUseCase.getCarts(testUserId);

        // then
        assertThat(result).hasSize(1);
        CartResponse cartResponse = result.get(0);

        assertThat(cartResponse.getUserId()).isEqualTo(testUserId);
        assertThat(cartResponse.getProductName()).isEqualTo("테스트 상품");
        assertThat(cartResponse.getProductPrice()).isEqualTo(new BigDecimal("10000.00"));
        assertThat(cartResponse.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 목록 조회 - 여러 상품")
    void getCarts_multipleProducts() {
        // given
        productService.create(ProductDomain.builder()
                .name("테스트 상품 2")
                .description("테스트 상품 설명 2")
                .price(new BigDecimal(20000))
                .stock(200)
                .build());

        cartService.add(CartDomain.builder()
                .userId(testUserId)
                .productId(1L)
                .quantity(2)
                .createdAt(LocalDateTime.now())
                .build());

        cartService.add(CartDomain.builder()
                .userId(testUserId)
                .productId(2L)
                .quantity(1)
                .createdAt(LocalDateTime.now())
                .build());

        // when
        List<CartResponse> result = cartUseCase.getCarts(testUserId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting("productName")
                .containsExactlyInAnyOrder("테스트 상품", "테스트 상품 2");
    }
}