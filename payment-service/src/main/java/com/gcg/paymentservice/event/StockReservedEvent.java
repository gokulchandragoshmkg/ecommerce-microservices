package com.gcg.paymentservice.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockReservedEvent {

	private Long orderId;
    private Long productId;
    private int quantity;

}
