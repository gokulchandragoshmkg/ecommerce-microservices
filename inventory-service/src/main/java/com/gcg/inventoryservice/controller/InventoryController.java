package com.gcg.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcg.inventoryservice.entity.Inventory;
import com.gcg.inventoryservice.service.InventoryService;

@RestController
@RequestMapping("v1/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@GetMapping("/{productId}")
	public ResponseEntity<Inventory> getStock(@PathVariable String productId){
		return ResponseEntity.ok(inventoryService.getStock(productId));
	}
	
	@PutMapping("/{productId}/reserve")
	public ResponseEntity<String> reserveStock(@PathVariable String productId,
            @RequestParam int quantity) {
		inventoryService.saveOrder(productId, quantity);
		return ResponseEntity.ok("Reserved successfully");
	}
}
