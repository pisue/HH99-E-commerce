package com.hh99.ecommerce.order.application.usecase;

import com.hh99.ecommerce.balance.domain.BalanceService;
import com.hh99.ecommerce.order.domain.dto.CreateOrderDto;
import com.hh99.ecommerce.order.domain.exception.OutOfStockException;
import com.hh99.ecommerce.order.domain.OrderService;
import com.hh99.ecommerce.product.interfaces.response.ProductResponse;
import com.hh99.ecommerce.product.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CreateOrderUsecase {
    private final OrderService orderService;
    private final ProductService productService;
    private final BalanceService balanceService;

    @Transactional
    public void execute(Long userId, Long productId, int quantity) {
        ProductResponse product = productService.getProduct(productId);
        if (product.getStock() < quantity) throw new OutOfStockException();

        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal userBalance = balanceService.getBalance(userId).getAmount();
        if (userBalance.compareTo(totalPrice) < 0) throw new IllegalStateException("잔액이 부족합니다.");

        orderService.createOrder(CreateOrderDto.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .price(product.getPrice())
                .build());

        productService.updateProductStock(product.getId(), product.getStock() - quantity);
    }
}
