package com.gcg.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
	
	@GetMapping("v1/inventory/{productId}")
	InventoryResponse getStock(@PathVariable String productId);
	
	@PutMapping("v1/inventory/{productId}/reserve")
	String reserveStock(@PathVariable String productId, @RequestParam int quantity);
}
