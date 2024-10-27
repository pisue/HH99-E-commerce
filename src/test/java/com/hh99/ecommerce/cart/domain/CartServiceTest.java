package com.hh99.ecommerce.cart.domain;

import com.hh99.ecommerce.cart.domain.exception.CartNotFoundException;
import com.hh99.ecommerce.cart.infra.jpa.Cart;
import com.hh99.ecommerce.cart.infra.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {}

    @Test
    @DisplayName("장바구니 추가")
    public void testAddCart() {
        //given
        Long userId = 2323L;
        Long productId = 1L;
        Integer quantity = 3;

        CartDomain cartDomain = CartDomain.generateCartDomain(userId, productId, quantity);
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        cartService.add(cartDomain);

        //then
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    @DisplayName("장바구니 삭제")
    public void testRemoveCart() {
        // given
        Long cartId = 1L;
        Cart cart = Cart.builder()
                .id(cartId)
                .userId(1L)
                .productId(1L)
                .quantity(2)
                .createdAt(LocalDateTime.now())
                .build();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> {
            Cart savedCart = invocation.getArgument(0);
            assertNotNull(savedCart.getDeletedAt());
            return savedCart;
        });

        // when
        cartService.delete(cartId);

        // then
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    @DisplayName("장바구니 정보가 존재하지 않을 때 CartNotFoundException")
    public void testRemoveCartNotFound() {
        // given
        Long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when
        assertThrows(CartNotFoundException.class, () -> cartService.delete(cartId));

        // then
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, never()).save(any(Cart.class));
    }
}