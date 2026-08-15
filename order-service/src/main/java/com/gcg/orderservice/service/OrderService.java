package com.gcg.orderservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.gcg.orderservice.client.InventoryClient;
import com.gcg.orderservice.entity.Order;
import com.gcg.orderservice.event.OrderCreatedEvent;
import com.gcg.orderservice.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private InventoryClient inventoryClient;

	@Autowired
	private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

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

		kafkaTemplate.send("order-created", event);

		return saved;
	}
	
	public Optional<Order> getOrderById(Long id) {
	    return orderRepository.findById(id);
	}
}
