package com.gcg.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gcg.inventoryservice.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, String>{

}
