package com.gcg.inventoryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcg.inventoryservice.entity.Inventory;
import com.gcg.inventoryservice.entity.Product;
import com.gcg.inventoryservice.entity.ProductRequest;
import com.gcg.inventoryservice.service.InventoryService;

@RestController
@RequestMapping(value = "/v1/inventoryservice")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@GetMapping("/{productId}")
	public ResponseEntity<Inventory> getStock(@PathVariable Long productId){
		Inventory inventory = inventoryService.getStock(productId);
		if(inventory == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(inventory);
	}
	
	@PutMapping("/{productId}/reserve")
	public ResponseEntity<String> reserveStock(@PathVariable Long productId,
            @RequestParam int quantity) {
		boolean reserved = inventoryService.tryReserveStock(productId, quantity);
		if(reserved) {
			return ResponseEntity.ok("Reserved successfully");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to reserve stock - insufficient quantity or product not found");
		}
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
	    return ResponseEntity.ok(inventoryService.getAllProducts());
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
		inventoryService.addProduct(productRequest);
	    return ResponseEntity.ok("Added successfully");
	}
}
