package com.gcg.inventoryservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcg.inventoryservice.entity.Inventory;
import com.gcg.inventoryservice.entity.Product;
import com.gcg.inventoryservice.entity.ProductRequest;
import com.gcg.inventoryservice.repository.InventoryRepository;
import com.gcg.inventoryservice.repository.ProductRepository;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private ProductRepository productRepository; 
	
	public Inventory getStock(Long productId) {
		return inventoryRepository.findByProductId(productId);
	}
	
	
	public void saveOrder(Long productId, int quantity) {
		Inventory inventory = inventoryRepository.findByProductId(productId);
		if( inventory.getAvailableQuantity() >= quantity) {
			inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
	        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
	        inventoryRepository.save(inventory);
		}
	}
	
	public List<Product> getAllProducts() {
	    return productRepository.findAll();
	}
	
	public void addProduct(ProductRequest productRequest) {
		
		Product p1 = new Product();
		p1.setProductName(productRequest.getProductName());
		p1.setDescription(productRequest.getDescription());
		p1.setCreatedOn(java.time.LocalDateTime.now());
		p1.setUpdatedOn(java.time.LocalDateTime.now());
		p1.setProductImage("default");
		Product p= productRepository.save(p1);
		
		Inventory inventory = new Inventory();
		inventory.setProductId(p.getId());
		inventory.setAvailableQuantity(productRequest.getQuantity());
		inventory.setReservedQuantity(0);
		
		inventoryRepository.save(inventory);
	}
	
	public boolean tryReserveStock(Long productId,int quality) {
		Inventory inventory = inventoryRepository.findByProductId(productId);
		if( quality <=inventory.getAvailableQuantity() ) { 
			inventory.setAvailableQuantity(inventory.getAvailableQuantity()-quality);
			inventory.setReservedQuantity(inventory.getReservedQuantity()+quality);
			inventoryRepository.save(inventory);
			return true;
		}
		
		return false;
		
	}
}
