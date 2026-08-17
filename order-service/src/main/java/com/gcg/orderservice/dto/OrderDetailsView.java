package com.gcg.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsView {

	private Long orderId;
	private String orderStatus;
	private Long productId;
	private int quantity;

	private String paymentStatus;
	private Float paidAmount;
	private String paymentMethod;

	private String latestNotificationMessage;
	private String latestNotificationType;

}
