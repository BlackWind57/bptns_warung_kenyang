package com.darryl.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.darryl.bean.FoodItem;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;


@ExtendWith(DBUnitExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class FoodRepositoryTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private FoodItemRepository repository;
	
	public ConnectionHolder getConnectionHolder() {
		return () -> dataSource.getConnection();
	}

	@Test
	@DataSet("foods.yml")
	// Please refer to src/test/resources/datasets/food.yml
	final void testFindByName() {
		FoodItem item = repository.findByName("Chicken Curry with Rice");
		assertEquals ( "Chicken Curry with Rice", item.getName() );
		
		item = repository.findByName("Fried Chicken with Rice");
		assertEquals ( "Fried Chicken with Rice", item.getName() );
	}
	
	@Test
	@DataSet("foods.yml")
	final void testFindByNameWithSubstringOfName() {
		FoodItem item = repository.findByName("Curry");
		assertEquals ( "Chicken Curry with Rice", item.getName(),
				"Should return the Chicken Curry with Rice");
	}
	
	@Test
	@DataSet("foods.yml")
	final void testFindByNameWithSubstringLowerCaseName () {
		NullPointerException e = assertThrows ( NullPointerException.class , () -> {
			FoodItem item = repository.findByName("curry");
			assertNull ( item.getName(), 
					"Should return null as the method did not allow to have case insensitive");
		});
		
		assertTrue ( e.getMessage().contains("is null") );
	}
	
	@Test
	@DataSet("foods.yml")
	final void testFindByNameWithMoreThanOneResult () {
		FoodItem item = repository.findByName("Rice");
		assertEquals ("Chicken Curry with Rice", item.getName(),
				"Should return the first occurence of Rice");
	}
}
