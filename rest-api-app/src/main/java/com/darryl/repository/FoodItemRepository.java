package com.darryl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.darryl.bean.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
	
	@Query ( value = "SELECT * FROM menu_item m WHERE m.name LIKE %?1% LIMIT 1",
			nativeQuery=true )
	FoodItem findByName ( String name );
}
