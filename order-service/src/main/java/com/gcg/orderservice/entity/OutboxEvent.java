package com.gcg.orderservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_event")
@Getter
@Setter
public class OutboxEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String aggregateType; // e.g. "Order"
	private String aggregateId; // the order ID
	private String eventType; // e.g. "orderCreated"

	@Column(columnDefinition = "TEXT")
	private String payload; // JSON string of the event

	private boolean published = false;
	private LocalDateTime createdAt;
	
}