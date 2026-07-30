package com.gcg.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcg.orderservice.client.InventoryClient;
import com.gcg.orderservice.client.InventoryResponse;
import com.gcg.orderservice.entity.Order;
import com.gcg.orderservice.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private InventoryClient inventoryClient;
	
	public Order placeOrder(String productId, int quantity) {
		InventoryResponse stock = inventoryClient.getStock(productId);

        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);

        if (stock.getAvailableQuantity() >= quantity) {
            inventoryClient.reserveStock(productId, quantity);
            order.setStatus("CONFIRMED");
        } else {
            order.setStatus("CANCELLED");
        }

        return orderRepository.save(order);
	}
}
