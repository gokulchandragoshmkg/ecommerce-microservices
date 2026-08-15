package com.gcg.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcg.orderservice.entity.Order;
import com.gcg.orderservice.service.OrderService;

@RestController
@RequestMapping(value = "/v1/orderservice")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/createOrder")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest  ) {
		return ResponseEntity.ok(orderService.placeOrder(orderRequest.getProductId(), orderRequest.getQuantity()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrder(@PathVariable Long id) {
	    return orderService.getOrderById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
}
