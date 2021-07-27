package com.darryl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darryl.bean.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
}
