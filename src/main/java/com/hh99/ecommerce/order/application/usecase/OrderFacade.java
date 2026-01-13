package com.hh99.ecommerce.order.application.usecase;

import com.hh99.ecommerce.balance.domain.BalanceService;
import com.hh99.ecommerce.order.application.dto.OrderItemCreateInfo;
import com.hh99.ecommerce.order.domain.OrderEventService;
import com.hh99.ecommerce.order.domain.OrderService;
import com.hh99.ecommerce.order.domain.dto.OrderDomain;
import com.hh99.ecommerce.order.domain.dto.OrderItemDomain;
import com.hh99.ecommerce.order.infra.entity.OrderOutbox;
import com.hh99.ecommerce.order.interfaces.request.OrderCreateRequest;
import com.hh99.ecommerce.order.interfaces.response.CreateOrderResponse;
import com.hh99.ecommerce.order.interfaces.response.OrderResponse;
import com.hh99.ecommerce.product.domain.ProductService;
import com.hh99.ecommerce.product.domain.dto.DeductStockInfo;
import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderService orderService;
    private final ProductService productService;
    private final BalanceService balanceService;
    private final OrderEventService orderEventService;

    public CreateOrderResponse createOrder(Long userId, List<OrderCreateRequest> requests) {
        List<OrderItemCreateInfo> orderItemCreateInfos = getOrderItemsInfo(userId, requests);
        List<DeductStockInfo> deductedStocks = new ArrayList<>();
        boolean isBalanceDeducted = false;
        BigDecimal totalPrice = BigDecimal.ZERO;

        try {
            // 재고 차감 처리
            for (OrderCreateRequest request : requests) {
                DeductStockInfo deductStockInfo = request.toDeductStockInfo();
                productService.deductProductStock(deductStockInfo);
                deductedStocks.add(deductStockInfo);
            }

            // 차감 포인트 계산
            totalPrice = orderItemCreateInfos.stream()
                    .map(OrderItemCreateInfo::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 포인트 차감
            balanceService.deduct(userId, totalPrice);
            isBalanceDeducted = true;

            // 주문 생성
            OrderDomain orderDomain = orderService.createOrder(userId, totalPrice);

            // 주문 상세정보 저장
            orderItemCreateInfos.forEach(orderItemCreateInfo ->
                    orderService.createOrderItem(orderItemCreateInfo.generateOrderItemDomain(orderDomain.getId()))
            );

            OrderOutbox orderOutbox = OrderOutbox.createFrom(orderDomain.toEntity(), requests);

            // 이벤트 발행
            orderEventService.saveAndPublishEvent(orderOutbox);

            // 응답 객체 생성
            return CreateOrderResponse.builder()
                    .id(orderDomain.getId())
                    .build();

        } catch (Exception e) {
            // 보상 트랜잭션 실행
            // 1. 재고 복구 (성공한 내역만)
            deductedStocks.forEach(productService::compensateStock);

            // 2. 잔액 복구 (차감에 성공했을 경우만)
            if (isBalanceDeducted) {
                balanceService.compensate(userId, totalPrice);
            }
            throw e;
        }
    }

    public List<OrderResponse> getOrders(Long userId) {
        return orderService.getOrders(userId).stream().map(orderDomain -> OrderResponse.builder()
                .id(orderDomain.getId())
                .orderDate(orderDomain.getOrderDate())
                .totalPrice(orderDomain.getTotalPrice())
                .orderItems(orderService.getOrderItemsByOrderId(orderDomain.getId()).stream()
                        .map(OrderItemDomain::toResponse)
                        .collect(Collectors.toList()))
                .build()).toList();
    }

    public OrderResponse getOrder(Long id) {
        OrderDomain orderDomain = orderService.getOrder(id);
        List<OrderItemDomain> orderItemDomains = orderService.getOrderItemsByOrderId(orderDomain.getId());
        return OrderResponse.builder()
                .id(orderDomain.getId())
                .orderDate(orderDomain.getOrderDate())
                .totalPrice(orderDomain.getTotalPrice())
                .status(orderDomain.getOrderStatus())
                .orderItems(orderItemDomains.stream()
                        .map(OrderItemDomain::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<OrderItemCreateInfo> getOrderItemsInfo(Long userId, List<OrderCreateRequest> requests) {
        return requests.stream()
                .map(request -> {
                    ProductDomain productDomain = productService.getProduct(request.getProductId());
                    return OrderItemCreateInfo.builder()
                            .userId(userId)
                            .productId(request.getProductId())
                            .quantity(request.getQuantity())
                            .price(productDomain.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                            .build();
                })
                .toList();
    }
}
