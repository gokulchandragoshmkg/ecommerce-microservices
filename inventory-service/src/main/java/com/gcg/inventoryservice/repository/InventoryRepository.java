package com.gcg.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcg.inventoryservice.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	public Inventory findByProductId(String productId);
}
