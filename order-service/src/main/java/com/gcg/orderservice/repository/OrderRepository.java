package com.gcg.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gcg.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
