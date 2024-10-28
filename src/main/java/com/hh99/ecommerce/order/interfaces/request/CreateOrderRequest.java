package com.hh99.ecommerce.order.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class CreateOrderRequest {
    private Long userId;
    private List<ProductOrderRequest> productOrderRequests;
}

