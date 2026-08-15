package com.gcg.orderservice.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.gcg.orderservice.event.PaymentCompletedEvent;
import com.gcg.orderservice.event.PaymentFailedEvent;
import com.gcg.orderservice.event.StockReservationFailedEvent;
import com.gcg.orderservice.event.StockReservedEvent;
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
    
    
    @KafkaListener(topics = "stock-reserved", groupId = "order-service")
	public void handleStockReserved(StockReservedEvent event) {
	    orderRepository.findById(event.getOrderId()).ifPresent(order -> {
	        order.setStatus("AWAITING_PAYMENT");
	        orderRepository.save(order);
	    });
	}
    
    @KafkaListener(topics = "payment-completed", groupId = "order-service")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus("CONFIRMED");
            orderRepository.save(order);
        });
    }

    @KafkaListener(topics = "payment-failed", groupId = "order-service")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus("CANCELLED");
            orderRepository.save(order);
        });
    }
}