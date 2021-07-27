package com.darryl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.darryl.bean.DrinkItem;
import com.darryl.bean.FoodItem;
import com.darryl.bean.MenuItem;
import com.darryl.bean.Orders;
import com.darryl.repository.DrinkItemRepository;
import com.darryl.repository.FoodItemRepository;
import com.darryl.repository.OrderRepository;
import com.darryl.requestbean.Order;
import com.darryl.requestbean.OrderItem;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	// System under test
	@InjectMocks
	private OrderService orderService;

	// Dependencies mocked
	@Mock
	private OrderRepository orderRepository;

	@Mock
	private FoodItemRepository foodRepository;

	@Mock
	private DrinkItemRepository drinkRepository;

	// Helper setup function
	OrderItem addOrderItem (
			String name, String type, Integer quantity ) {

		com.darryl.requestbean.MenuItem menuItem =
				new com.darryl.requestbean.MenuItem (name, type);

		return new OrderItem ( quantity, menuItem );
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	final void testGetOrders() {
		List<Orders> mockOrders = new ArrayList<>() {{
			add ( new Orders ( 1 ) );
			add ( new Orders ( 2 ) );
			add ( new Orders ( 100 ) );
		}};

		Mockito.when(orderRepository.findAll())
			.thenReturn( mockOrders );

		List<Orders> orders = orderRepository.findAll();

		assertEquals( 1, orders.get(0).getTableNumber() );
		assertEquals( 2, orders.get(1).getTableNumber() );
		assertEquals( 100, orders.get(2).getTableNumber() );
		assertEquals( 3, orders.size());
	}
	
	@Test 
	final void testSaveOrder () {
		// Sample created order from orderService
		Orders createdOrder = new Orders ( 1L, 5, new ArrayList<>() {{
			add ( 
				new com.darryl.bean.OrderItem(
					1, 
					new com.darryl.bean.FoodItem ( 
						"Chips", 
						BigDecimal.valueOf(1), 
						"Snacks"),
					null
				)
			);
		}});
		
		Mockito.when(orderRepository.saveAndFlush(createdOrder))
			.thenReturn (createdOrder);
		
		orderRepository.saveAndFlush( createdOrder );
		
		assertEquals ( 1L, createdOrder.getId(), "Id is 1" );
		assertEquals ( 5, createdOrder.getTableNumber(),
				"Table number should be 5" );
		assertEquals ( 1, createdOrder.getOrderItems().get(0).getQuantity(),
				"Quantity should be 1" );
		assertEquals ( "Chips", createdOrder.getOrderItems().get(0).getMenuItem().getName(),
				"Name should be chips");
		assertEquals ( BigDecimal.valueOf(1),
				createdOrder.getOrderItems().get(0).getMenuItem().getPrice() );
	}
	
	@Test 
	final void testValidInputOfOrders () {
		// Sample created order from orderService
		Orders createdOrder = new Orders ( null, new ArrayList<>() {{
			add ( 
				new com.darryl.bean.OrderItem(
					1, 
					new com.darryl.bean.FoodItem ( 
						"Chips", 
						BigDecimal.valueOf(1), 
						"Snacks"),
					null
				)
			);
		}});
		
		Mockito.when(orderRepository.saveAndFlush(createdOrder))
			.thenReturn (createdOrder);
		
		orderRepository.saveAndFlush( createdOrder );
		
		assertNull ( createdOrder.getId(), 
				"Id is null because id incremented automatically on the db" );
		assertEquals ( 5, createdOrder.getTableNumber(),
				"Table number should be 5" );
		assertEquals ( 1, createdOrder.getOrderItems().get(0).getQuantity(),
				"Quantity should be 1" );
		assertEquals ( "Chips", createdOrder.getOrderItems().get(0).getMenuItem().getName(),
				"Name should be chips");
		assertEquals ( BigDecimal.valueOf(1),
				createdOrder.getOrderItems().get(0).getMenuItem().getPrice() );
	}

	// Testing add order before being pushed to database
	@Test
	final void testAddOrderForOneFoodAndDrink() {
		// Sample Order generated automatically to bean.request models
		Order mockOrder = new Order ( 100, new ArrayList<>() {{
			add ( addOrderItem ( "Chicken Curry with Rice", "food", 2 ) );
			add ( addOrderItem ( "Ice Tea", "drink", 1 ) );
		}});
		
		// Mock generated food item and drink item
		Mockito.when(foodRepository.findByName("Chicken Curry with Rice"))
			.thenReturn(
				new FoodItem (
					"Chicken Curry with Rice",
					BigDecimal.valueOf(11),
					"Main Course" )
				);
		
		Mockito.when(drinkRepository.findByName("Ice Tea"))
			.thenReturn(
				new DrinkItem (
					"Ice Tea",
					BigDecimal.valueOf(1) )
				);		

		Orders orderCreated = orderService.addOrders(mockOrder);
		
		assertNotNull ( orderCreated );

		// Making sure the order details are correct as expected
		assertEquals ( "PENDING", orderCreated.getStatus(), 
				"default status of order should be pending" );
		assertNull ( orderCreated.getId(), 
				"order id should be null due to not being pushed to db yet" );
		assertEquals ( 100, orderCreated.getTableNumber(), 
				"table number should be based on request" );

		// Making sure the food order item  are correct as expected
		assertEquals ( 2, orderCreated.getOrderItems().get(0).getQuantity(), 
				"quantity should be based on request" );
		assertNull ( orderCreated.getOrderItems().get(0).getId(), 
				"order item id should be null due to not being pushed to db yet" );

		// Making sure the food menu item details are correct as expected
		assertEquals ( "Chicken Curry with Rice",
				orderCreated.getOrderItems().get(0).getMenuItem().getName(),
				"food name should be based on request");
		assertEquals ( BigDecimal.valueOf(11),
				orderCreated.getOrderItems().get(0).getMenuItem().getPrice(),
				"price should be based on the mock database value of WK");
		assertEquals ( "Main Course",
				((FoodItem) orderCreated.getOrderItems()
						.get(0)
							.getMenuItem())
								.getDishType(),
				"dish type should be based on the mock database value of WK");
		
		// Making sure the drink order item are correct as expected
		assertEquals ( 1, orderCreated.getOrderItems().get(1).getQuantity(),
				"quantity should be based on request");
		assertNull ( orderCreated.getOrderItems().get(1).getId(),
				"order item should be null due to not yet being pushed to db");
		
		// Making sure the drink menu item details are correct as expected
		assertEquals ( "Ice Tea", 
				orderCreated.getOrderItems().get(1).getMenuItem().getName(),
				"drink name should be based on request");
		assertEquals ( BigDecimal.valueOf(1), 
				orderCreated.getOrderItems().get(1).getMenuItem().getPrice(),
				"price should be based on the mock database of WK");
	}

	

}
