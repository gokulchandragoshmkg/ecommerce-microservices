package com.gcg.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcg.inventoryservice.entity.Inventory;
import com.gcg.inventoryservice.repository.InventoryRepository;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	public Inventory getStock(String productId) {
		return inventoryRepository.findById(productId).get();
	}
	
	
	public void saveOrder(String productId, int quantity) {
		Inventory inventory = inventoryRepository.findById(productId).get();
		if( inventory.getAvailableQuantity() >= quantity) {
			inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
	        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
	        inventoryRepository.save(inventory);
		}
	}
}
