package com.hh99.ecommerce.order.application.mapper;

import com.hh99.ecommerce.balance.domain.BalanceService;
import com.hh99.ecommerce.order.application.dto.OrderValidationResult;
import com.hh99.ecommerce.order.domain.OrderService;
import com.hh99.ecommerce.order.domain.dto.CreateOrderDto;
import com.hh99.ecommerce.order.domain.exception.OutOfStockException;
import com.hh99.ecommerce.product.domain.ProductService;
import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final ProductService productService;
    private final BalanceService balanceService;

    public OrderValidationResult validateAndCreateOrderDetails(Long userId, Long productId, int quantity) {
        ProductDomain productDomain = productService.getProduct(productId);
        BigDecimal totalPrice = calculateTotalPrice(productDomain.getPrice(), quantity);

        validateStock(productDomain, quantity);
        validateBalance(userId, totalPrice);

        return OrderValidationResult.builder()
                .productDomain(productDomain)
                .totalPrice(totalPrice)
                .createOrderDto(createOrderDto(userId, productId, quantity, productDomain.getPrice()))
                .build();

    }

    private CreateOrderDto createOrderDto(Long userId, Long productId, int quantity, BigDecimal price) {
        return CreateOrderDto.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .price(price)
                .build();
    }

    private void validateStock(ProductDomain product, int quantity) {
        if (product.getStock() < quantity) {
            throw new OutOfStockException();
        }
    }

    private void validateBalance(Long userId, BigDecimal totalPrice) {
        BigDecimal userBalance = balanceService.getBalance(userId).getAmount();
        if (userBalance.compareTo(totalPrice) < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        }
    }

    private BigDecimal calculateTotalPrice(BigDecimal price, int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
