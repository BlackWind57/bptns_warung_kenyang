package com.darryl.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.darryl.bean.DrinkItem;
import com.darryl.bean.FoodItem;
import com.darryl.repository.DrinkItemRepository;
import com.darryl.repository.FoodItemRepository;
import com.darryl.requestbean.MenuItem;

@ExtendWith(MockitoExtension.class)
class MenuItemTest {
	
	// System under test
	@InjectMocks
	private MenuItemService menuItemService;
	
	@Mock
	private FoodItemRepository foodRepository;

	@Mock
	private DrinkItemRepository drinkRepository;

	@Test
	final void testGetMenuItemWithValidInput() {
		MenuItem mockFood = new MenuItem ( "Beef Stew with Rice", "food" );
		MenuItem mockDrink = new MenuItem ( "Ice Coffee", "drink" );
		
		Mockito.when( foodRepository.findByName(mockFood.getName()) )
		.thenReturn( new FoodItem ( 
				"Beef Stew with Rice", 
				BigDecimal.valueOf(12),
				"Main Course"
		));
		
		Mockito.when( drinkRepository.findByName(mockDrink.getName() ) )
		.thenReturn ( new DrinkItem ("Ice Coffee", BigDecimal.valueOf(1) ) );
		
		FoodItem foundFoodItem = 
				(FoodItem) menuItemService.getMenuItem(mockFood);
		
		assertEquals ( "Beef Stew with Rice", foundFoodItem.getName(),
				"Should found beef stew with rice" );
		assertEquals ( BigDecimal.valueOf(12), foundFoodItem.getPrice(), 
				"Should return the price of the food");
		assertEquals ( "Main Course", foundFoodItem.getDishType(),
				"Should return main course");
		
		DrinkItem foundDrinkItem =
				(DrinkItem) menuItemService.getMenuItem(mockDrink);
		
		assertEquals ("Ice Coffee", foundDrinkItem.getName(),
				"Should return ice coffee");
		assertEquals ( BigDecimal.valueOf(1), foundDrinkItem.getPrice(),
				"Should return price of the drink");
	}
	
	@Test
	final void testGetMenuItemWithNullInput() {
		
		ResponseStatusException e = assertThrows ( ResponseStatusException.class , () -> {
			MenuItem mockFood = new MenuItem ( null, "food" );
			
			Mockito.when( foodRepository.findByName(mockFood.getName()) )
			.thenReturn( new FoodItem ( 
					null, 
					BigDecimal.valueOf(12),
					"Main Course"
			));
			
			FoodItem foundFoodItem = 
					(FoodItem) menuItemService.getMenuItem(mockFood);
			
			assertEquals ( "Main Course", foundFoodItem.getDishType(),
					"Should return main course");
		});
		
		assertEquals ( e.getStatus().toString(), "400 BAD_REQUEST");
		assertTrue ( e.getMessage().contains("Invalid") );
		
		e = assertThrows ( ResponseStatusException.class , () -> {
			MenuItem mockFood = new MenuItem ( "Beef Stew with Rice", null );
			
			Mockito.when( foodRepository.findByName(mockFood.getName()) )
			.thenReturn( new FoodItem ( 
					"Beef Stew with Rice", 
					BigDecimal.valueOf(12),
					"Main Course"
			));
			
			FoodItem foundFoodItem = 
					(FoodItem) menuItemService.getMenuItem(mockFood);
			
			assertEquals ( "Beef Stew with Rice", foundFoodItem.getName(),
					"Should found beef stew with rice" );
		});
		
		assertEquals ( e.getStatus().toString(), "400 BAD_REQUEST");
		assertTrue ( e.getMessage().contains("Invalid") );
		
	}
	
	@Test
	final void testGetMenuItemWithEmptyStringInput() {
		
		ResponseStatusException e = assertThrows ( ResponseStatusException.class , () -> {
			MenuItem mockFood = new MenuItem ( "", "food" );
			
			Mockito.when( foodRepository.findByName(mockFood.getName()) )
			.thenReturn( new FoodItem ( 
					"", 
					BigDecimal.valueOf(12),
					"Main Course"
			));
			
			FoodItem foundFoodItem = 
					(FoodItem) menuItemService.getMenuItem(mockFood);
			
			assertEquals ( "Main Course", foundFoodItem.getDishType(),
					"Should return main course");
		});
		
		assertEquals ( e.getStatus().toString(), "400 BAD_REQUEST");
		assertTrue ( e.getMessage().contains("Invalid") );
		
		e = assertThrows ( ResponseStatusException.class , () -> {
			MenuItem mockFood = new MenuItem ( "Beef Stew with Rice", "" );
			
			Mockito.when( foodRepository.findByName(mockFood.getName()) )
			.thenReturn( new FoodItem ( 
					"Beef Stew with Rice", 
					BigDecimal.valueOf(12),
					"Main Course"
			));
			
			FoodItem foundFoodItem = 
					(FoodItem) menuItemService.getMenuItem(mockFood);
			
			assertEquals ( "Beef Stew with Rice", foundFoodItem.getName(),
					"Should found beef stew with rice" );
		});
		
		assertEquals ( e.getStatus().toString(), "400 BAD_REQUEST");
		assertTrue ( e.getMessage().contains("Invalid") );
	}

	
}
