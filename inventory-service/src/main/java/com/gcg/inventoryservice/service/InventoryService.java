package com.gcg.inventoryservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	
	@Cacheable(value = "inventory",key = "#productId")
	public Inventory getStock(Long productId) {
		return inventoryRepository.findByProductId(productId);
	}
	
	@CacheEvict(value = "inventory", key = "#productId")
	public boolean saveOrder(Long productId, int quantity) {
		Inventory inventory = inventoryRepository.findByProductId(productId);
		if(inventory != null && inventory.getAvailableQuantity() >= quantity) {
			inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
	        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
	        inventoryRepository.save(inventory);
	        return true;
		}
		return false;
	}
	
	@Cacheable(value = "products",key = "'all'")
	public List<Product> getAllProducts() {
	    return productRepository.findAll();
	}
	
	@CacheEvict(value = "products", key = "'all'")
	public void addProduct(ProductRequest productRequest) {
		
		Product p1 = new Product();
		p1.setProductName(productRequest.getProductName());
		p1.setDescription(productRequest.getDescription());
		p1.setDiscountPrice(productRequest.getDiscountPrice());
		p1.setPrice(productRequest.getPrice());
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
	
	@CacheEvict(value = "inventory",key = "#productId")
	public boolean tryReserveStock(Long productId, int quantity) {
		Inventory inventory = inventoryRepository.findByProductId(productId);
		if(inventory != null && quantity <= inventory.getAvailableQuantity()) { 
			inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
			inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
			inventoryRepository.save(inventory);
			return true;
		}
		
		return false;
		
	}
}
