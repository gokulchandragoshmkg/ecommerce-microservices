package com.gcg.orderservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcg.orderservice.client.InventoryClient;
import com.gcg.orderservice.client.NotificationClient;
import com.gcg.orderservice.client.PaymentClient;
import com.gcg.orderservice.client.PaymentDetailResponse;
import com.gcg.orderservice.dto.OrderDetailsView;
import com.gcg.orderservice.entity.Order;
import com.gcg.orderservice.entity.OutboxEvent;
import com.gcg.orderservice.event.OrderCreatedEvent;
import com.gcg.orderservice.repository.OrderRepository;
import com.gcg.orderservice.repository.OutboxEventRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private InventoryClient inventoryClient;

	@Autowired
	private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

	@Autowired
	private OutboxEventRepository outboxEventRepository;

	@Autowired
	private PaymentClient paymentClient;

	@Autowired
	private NotificationClient notificationClient;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Transactional
	public Order placeOrder(Long productId, int quantity) {
		Order order = new Order();
		order.setProductId(productId);
		order.setQuantity(quantity);
		order.setStatus("PENDING");
		Order saved = orderRepository.save(order);

		OrderCreatedEvent event = new OrderCreatedEvent();
		event.setOrderId(saved.getId());
		event.setProductId(productId);
		event.setQuantity(quantity);

		try {
			OutboxEvent outbox = new OutboxEvent();
			outbox.setAggregateType("Order");
			outbox.setAggregateId(saved.getId().toString());
			outbox.setEventType("orderCreated");
			outbox.setPayload(objectMapper.writeValueAsString(event));
			outbox.setPublished(false);
			outbox.setCreatedAt(LocalDateTime.now());
			outboxEventRepository.save(outbox);
		} catch (Exception e) {
			throw new RuntimeException("Failed to serialize outbox event", e);
		}

		// kafkaTemplate.send("order-created", event);

		return saved;
	}

	public Optional<Order> getOrderById(Long id) {
		return orderRepository.findById(id);
	}

	public OrderDetailsView getOrderDetails(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		OrderDetailsView view = new OrderDetailsView();
		view.setOrderId(order.getId());
		view.setOrderStatus(order.getStatus());
		view.setProductId(order.getProductId());
		view.setQuantity(order.getQuantity());

		try {
			List<PaymentDetailResponse> payments = paymentClient.getPaymentsByOrder(orderId);
			if (!payments.isEmpty()) {
				PaymentDetailResponse latest = payments.get(payments.size() - 1);
				view.setPaymentStatus(latest.getPaymentStatus());
				view.setPaidAmount(latest.getPaidAmount());
				view.setPaymentMethod(latest.getPaymentMethod());
			}
		} catch (Exception e) {

		}

		try {
			List<Map<String, Object>> notifications = notificationClient.getNotificationsByOrder(orderId);
			if (!notifications.isEmpty()) {
				Map<String, Object> latest = notifications.get(notifications.size() - 1);
				view.setLatestNotificationMessage((String) latest.get("message"));
				view.setLatestNotificationType((String) latest.get("type"));
			}
		} catch (Exception e) {

		}

		return view;
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}
}
