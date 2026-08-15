package com.gcg.inventoryservice.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCompletedEvent {
    private Long orderId;
}