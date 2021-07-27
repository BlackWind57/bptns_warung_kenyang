package com.darryl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.darryl.bean.Orders;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
	
	@Query ( value = "SELECT * FROM orders o "
			+ "WHERE o.table_num = ?1 "
			+ "LIMIT 1",
			nativeQuery=true )
	Orders findByTableNumber ( Integer tableNumber );
}
