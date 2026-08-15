package com.gcg.paymentservice.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
	private Long orderId;
	private Long productId;
	private int quantity;
	private String cardNumber;
	private String bankName;
	// getters/setters
}