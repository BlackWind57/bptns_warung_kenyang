package com.darryl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.darryl.bean.DrinkItem;

public interface DrinkItemRepository extends JpaRepository<DrinkItem, Long> {
	
	DrinkItem findByName ( String name );
	
}
