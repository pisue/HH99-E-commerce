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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    @DisplayName("유저 정보로 장바구니 목록 조회")
    public void testGetCarts() {
        // given
        Long userId = 1L;
        Cart cart1 = Cart.builder()
                .id(1L)
                .userId(userId)
                .productId(1L)
                .quantity(3)
                .createdAt(LocalDateTime.now())
                .build();

        Cart cart2 = Cart.builder()
                .id(2L)
                .userId(userId)
                .productId(3L)
                .quantity(5)
                .createdAt(LocalDateTime.now())
                .build();

        List<Cart> carts = List.of(cart1, cart2);
        when(cartRepository.findByUserIdAndDeletedAtIsNull(userId)).thenReturn(carts);

        // when
        List<CartDomain> result = cartService.getCarts(userId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        /* 이 부분에 carts.toDomain과 result가 같은 값인지 확인하는 로직 추가가 필요함 */
        verify(cartRepository, times(1)).findByUserIdAndDeletedAtIsNull(userId);
    }

}