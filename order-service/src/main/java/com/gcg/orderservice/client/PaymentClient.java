package com.gcg.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "payment-service")
public interface PaymentClient {
    @GetMapping("/v1/paymentservice/order/{orderId}")
    List<PaymentDetailResponse> getPaymentsByOrder(@PathVariable Long orderId);
}