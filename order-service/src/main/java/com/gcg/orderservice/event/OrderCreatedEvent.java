package com.gcg.orderservice.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreatedEvent {
    private Long orderId;
    private Long productId;
    private int quantity;

}