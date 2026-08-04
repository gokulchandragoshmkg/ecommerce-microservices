package com.gcg.orderservice.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockReservationFailedEvent {
	private Long orderId;
}
