package com.darryl.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.darryl.bean.DrinkItem;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;


@ExtendWith(DBUnitExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class DrinkRepositoryTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private DrinkItemRepository repository;

	public ConnectionHolder getConnectionHolder() {
		return () -> dataSource.getConnection();
	}
	
	@Test
	@DataSet("menu.yml")
	// Please refer to src/test/resources/datasets/menu.yml
	final void testFindByName() {
		DrinkItem item = repository.findByName("Ice Lemon Tea");
		assertEquals ( "Ice Lemon Tea", item.getName() );
		
		item = repository.findByName("Ice Tea");
		assertEquals ( "Ice Tea", item.getName() );
	}
	
	@Test
	@DataSet("menu.yml")
	final void testFindByNameWithSubstringOfName() {
		DrinkItem item = repository.findByName("Lemon");
		assertEquals ( "Ice Lemon Tea", item.getName(),
				"Should return the Ice Lemon Tea");
	}
	
	@Test
	@DataSet("menu.yml")
	final void testFindByNameWithSubstringLowerCaseName () {
		NullPointerException e = assertThrows ( NullPointerException.class , () -> {
			DrinkItem item = repository.findByName("lemon");
			assertNull ( item.getName(), 
					"Should return null as the method did not allow to have case insensitive");
		});
		
		assertTrue ( e.getMessage().contains("is null") );
	}
	
	@Test
	@DataSet("menu.yml")
	final void testFindByNameWithMoreThanOneResult () {
		DrinkItem item = repository.findByName("Tea");
		assertEquals ("Ice Lemon Tea", item.getName(),
				"Should return the first occurence of Tea");
	}
	
	@Test
	@DataSet("menu.yml")
	final void testFindByNameWithNullValue () {
		NullPointerException e = assertThrows ( NullPointerException.class , () -> {
	
			DrinkItem item = repository.findByName(null);
			assertEquals ("Ice Lemon Tea", item.getName(),
					"Should return the first occurence of Tea");
		});
		
		assertTrue ( e.getMessage().contains("is null") );
	}
	
	@Test
	@DataSet("menu.yml")
	final void testFindByNameWithEmptyString () {
		// This empty string check is done prior to querying the database
		DrinkItem item = repository.findByName("");
		assertEquals ("Ice Lemon Tea", item.getName(),
				"Should return the first occurence of Tea");		
	}
	
	@Test
	@DataSet("menu.yml")
	final void testFindByNameWithFoodName () {
		NullPointerException e = assertThrows ( NullPointerException.class , () -> {

			DrinkItem item = repository.findByName("Chicken Curry with Rice");
			assertEquals ("Chicken Curry with Rice", item.getName(),
					"Should return the first occurence Chicken Curry with Rice");
		});
		assertTrue ( e.getMessage().contains("is null") );
	}

}
