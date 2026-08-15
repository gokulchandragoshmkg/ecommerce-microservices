package com.gcg.notificationservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity	
@Table(name = "notification_log")
public class NotificationLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String message;
    private String type; // e.g., ORDER_CREATED, PAYMENT_SUCCESS, PAYMENT_FAILED
    private LocalDateTime sentAt;

}
