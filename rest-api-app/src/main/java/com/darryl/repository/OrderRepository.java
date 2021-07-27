package com.darryl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darryl.bean.Orders;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
	
}
