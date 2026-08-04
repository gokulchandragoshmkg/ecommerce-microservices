package com.gcg.inventoryservice.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.gcg.inventoryservice.service.InventoryService;

@Service
public class InventoryEventListener {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	@KafkaListener(topics = "order-created", groupId = "inventory-service")
	public void handleOrderCreated(OrderCreatedEvent event) {
		boolean reserved = inventoryService.tryReserveStock(event.getProductId(), event.getQuantity());

		if (reserved) {
			StockReservedEvent success = new StockReservedEvent();
			success.setOrderId(event.getOrderId());
			success.setProductId(event.getProductId());
			success.setQuantity(event.getQuantity());
			kafkaTemplate.send("stock-reserved", success);
		} else {
			StockReservationFailedEvent failed = new StockReservationFailedEvent();
			failed.setOrderId(event.getOrderId());
			kafkaTemplate.send("stock-reservation-failed", failed);
		}
	}
}