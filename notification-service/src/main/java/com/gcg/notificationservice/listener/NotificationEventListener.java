package com.gcg.notificationservice.listener;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.gcg.notificationservice.entity.NotificationLog;
import com.gcg.notificationservice.event.OrderCreatedEvent;
import com.gcg.notificationservice.event.PaymentCompletedEvent;
import com.gcg.notificationservice.event.PaymentFailedEvent;
import com.gcg.notificationservice.repository.NotificationLogRepository;

@Component
public class NotificationEventListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventListener.class);

    @Autowired
    private NotificationLogRepository notificationLogRepository;

    @KafkaListener(topics = "order-created", groupId = "notification-service")
    public void handleOrderCreated(OrderCreatedEvent event) {
        String message = "Your order #" + event.getOrderId() + " has been placed successfully.";
        saveAndLog(event.getOrderId(), message, "ORDER_CREATED");
    }

    @KafkaListener(topics = "payment-completed", groupId = "notification-service")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        String message = "Payment for order #" + event.getOrderId() + " was successful. Your order is confirmed!";
        saveAndLog(event.getOrderId(), message, "PAYMENT_SUCCESS");
    }

    @KafkaListener(topics = "payment-failed", groupId = "notification-service")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        String message = "Payment for order #" + event.getOrderId() + " failed. Your order has been cancelled.";
        saveAndLog(event.getOrderId(), message, "PAYMENT_FAILED");
    }

    private void saveAndLog(Long orderId, String message, String type) {
        NotificationLog notification = new NotificationLog();
        notification.setOrderId(orderId);
        notification.setMessage(message);
        notification.setType(type);
        notification.setSentAt(LocalDateTime.now());
        notificationLogRepository.save(notification);

        // Simulates sending an email/SMS — just logs for this project
        log.info("NOTIFICATION SENT [{}] -> {}", type, message);
    }
}