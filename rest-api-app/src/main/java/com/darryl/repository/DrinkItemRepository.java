package com.darryl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.darryl.bean.DrinkItem;

public interface DrinkItemRepository extends JpaRepository<DrinkItem, Long> {
	@Query ( value = "SELECT * FROM menu_item m "
			+ "WHERE m.name LIKE %?1% AND m.menu_type = 'Drinks' "
			+ "LIMIT 1",
			nativeQuery=true )
	DrinkItem findByName ( String name );
	
}
