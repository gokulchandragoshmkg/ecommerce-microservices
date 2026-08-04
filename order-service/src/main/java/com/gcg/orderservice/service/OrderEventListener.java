package com.gcg.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.gcg.orderservice.event.StockReservationFailedEvent;
import com.gcg.orderservice.repository.OrderRepository;

@Component
public class OrderEventListener {

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "stock-reservation-failed", groupId = "order-service")
    public void handleStockFailed(StockReservationFailedEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus("CANCELLED");
            orderRepository.save(order);
        });
    }
    // similarly handle payment-completed / payment-failed once Payment Service exists
}