package com.gcg.gatewayservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/fallback/order")
    public Mono<String> orderFallback() {
        return Mono.just("Order Service is currently unavailable. Please try again shortly.");
    }

    @GetMapping("/fallback/inventory")
    public Mono<String> inventoryFallback() {
        return Mono.just("Inventory Service is currently unavailable. Please try again shortly.");
    }

    @GetMapping("/fallback/payment")
    public Mono<String> paymentFallback() {
        return Mono.just("Payment Service is currently unavailable. Please try again shortly.");
    }
}