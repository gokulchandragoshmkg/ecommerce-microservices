package com.gcg.orderservice.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetailResponse {
    private Long id;
    private Long productId;
    private Long userId;
    private String paymentStatus;
    private float paidAmount;
    private String paymentMethod;
    private String bankName;

   
}