package com.gcg.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcg.inventoryservice.entity.Inventory;
import com.gcg.inventoryservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
