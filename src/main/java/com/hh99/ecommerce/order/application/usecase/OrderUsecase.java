package com.hh99.ecommerce.order.application.usecase;

import com.hh99.ecommerce.balance.domain.BalanceService;
import com.hh99.ecommerce.order.application.dto.OrderValidationResult;
import com.hh99.ecommerce.order.application.mapper.OrderMapper;
import com.hh99.ecommerce.order.domain.dto.CreateOrderDto;
import com.hh99.ecommerce.order.domain.dto.OrderDomain;
import com.hh99.ecommerce.order.domain.exception.OutOfStockException;
import com.hh99.ecommerce.order.domain.OrderService;
import com.hh99.ecommerce.order.interfaces.request.ProductOrderRequest;
import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import com.hh99.ecommerce.product.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderUsecase {
    private final OrderService orderService;
    private final ProductService productService;
    private final OrderMapper orderMapper;
    private final BalanceService balanceService;

    @Transactional
    public void createOrder(Long userId, List<ProductOrderRequest> productOrderRequests) {
        List<OrderValidationResult> validationResults = productOrderRequests.stream()
                .map(productOrderRequest -> {
                    OrderValidationResult result = orderMapper.validateAndCreateOrderDetails(
                        userId, 
                        productOrderRequest.getProductId(), 
                        productOrderRequest.getQuantity()
                    );
                    productService.updateProductStock(
                        result.getProductDomain().getId(),
                        result.getProductDomain().getStock() - productOrderRequest.getQuantity()
                    );
                    return result;
                })
                .toList();

        BigDecimal totalPrice = validationResults.stream()
                .map(OrderValidationResult::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 유저의 포인트 잔액 확인
        BigDecimal userBalance = balanceService.getBalance(userId).getAmount();
        if (userBalance.compareTo(totalPrice) < 0) {
            throw new RuntimeException("포인트 잔액이 부족합니다.");
        }

        // 포인트 차감
        balanceService.deductBalance(userId, totalPrice);

        OrderDomain orderDomain = orderService.createOrder(userId, totalPrice);

        validationResults.forEach(result ->
            orderService.createOrderItem(orderDomain.getId(), result.getCreateOrderDto())
        );
    }
}
