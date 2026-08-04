package com.gcg.inventoryservice.event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderCreatedEvent {

	private Long orderId;
    private Long productId;
    private int quantity;
}
