package com.gcg.inventoryservice.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockReservationFailedEvent {
	private Long orderId;
}
