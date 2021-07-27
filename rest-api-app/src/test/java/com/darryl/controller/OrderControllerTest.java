package com.darryl.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.darryl.bean.DrinkItem;
import com.darryl.bean.FoodItem;
import com.darryl.bean.Orders;
import com.darryl.requestbean.MenuItem;
import com.darryl.requestbean.Order;
import com.darryl.requestbean.OrderItem;
import com.darryl.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;



@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@Test
	@DisplayName("GET /orders success")
	final void testGetAllOrders() throws Exception {
		Orders order1 = new Orders ( 5, Lists.newArrayList(
				new com.darryl.bean.OrderItem(
					1,
					new com.darryl.bean.FoodItem (
						"Chips",
						BigDecimal.valueOf(1),
						"Snacks"),
					null
				)
		));
		Orders order2 = new Orders ( 6, Lists.newArrayList(
				new com.darryl.bean.OrderItem(
					1,
					new com.darryl.bean.DrinkItem (
						"Ice Coffee",
						BigDecimal.valueOf(1)
					),
					null
				)
		));

		Mockito.when( orderService.getAllOrders() )
			.thenReturn( Lists.newArrayList( order1, order2 )
					.stream()
					.map(com.darryl.responsebean.OrderResponse::new)
					.collect(Collectors.toList()));

		// Execute the GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders"))
			// Validate response code and content type
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))


			// Validate headers
			.andExpect(header().string(HttpHeaders.LOCATION, "/orders"))

			// Validate the returned fields
			.andExpect(jsonPath("$[0].tableNumber", is(5) ))
			.andExpect(jsonPath("$[0].orderItems[0].quantity", is(1) ))
			.andExpect(jsonPath("$[0].orderItems[0].menuItem", is("FoodItem [name= Chips, price= $1]") ))
			.andExpect(jsonPath("$[1].tableNumber", is(6) ))
			.andExpect(jsonPath("$[1].orderItems[0].quantity", is(1) ))
			.andExpect(jsonPath("$[1].orderItems[0].menuItem", is("DrinkItem [name= Ice Coffee, price= $1]") ));
	}

	@Test
	@DisplayName("POST /api/v1/orders/10")
	final void testSuccessfulAddOrder() throws Exception {
		// Setup mocked objects
		OrderItem orderItem1 = new OrderItem ( 2, new MenuItem( "French Fries", "food" ) );
		OrderItem orderItem2 = new OrderItem ( 2, new MenuItem ( "Fried Noodles", "food" ) );
		OrderItem orderItem3 = new OrderItem ( 3, new MenuItem ( "Juice", "drink" ) );
		Order orderMock = new Order (
			10,
			Lists.newArrayList(
				orderItem1, 
				orderItem2, 
				orderItem3
			) 
		);
		com.darryl.bean.OrderItem ordersItem1 = new com.darryl.bean.OrderItem(
			2, 
			new FoodItem ( "French Fries", BigDecimal.valueOf(3), "Side Dishes" ),
			null
		);
		com.darryl.bean.OrderItem ordersItem2 = new com.darryl.bean.OrderItem(
			2, 
			new FoodItem ( "Fried Noodles", BigDecimal.valueOf(5), "Main Course" ),
			null
		);
		com.darryl.bean.OrderItem ordersItem3 = new com.darryl.bean.OrderItem(
			3, 
			new DrinkItem ( "Juice", BigDecimal.valueOf(2) ),
			null
		);
		
		Orders createdOrder = new Orders ( 
				1L,
				10,
				Lists.newArrayList( ordersItem1, ordersItem2, ordersItem3 )
		);
		
		Mockito.when(orderService.saveOrder(any(Order.class) ))
			.thenReturn( createdOrder );
		
		// Execute the POST request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString (orderMock) ))
				
				.andExpect (status().isCreated())
				.andExpect(header().string(
						HttpHeaders.LOCATION, 
						"http://localhost/api/v1/orders/1")
				);
	}
	
	@Test
	@DisplayName("POST /api/v1/orders/id - Empty Table Number")
	final void testAddOrderWithEmptyTableNumber() throws Exception {
		// Setup mocked objects
		Mockito.when(orderService.saveOrder( new Order() ))
			.thenReturn( new Orders()  );
		
		// Execute the POST request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString ( new Order() ) ))
				
				.andExpect (status().isBadRequest());
	}
	
	@Test
	@DisplayName("POST /api/v1/orders/id - Empty Order Items")
	final void testAddOrderWithInvalidTableNumber() throws Exception {
		// Setup mocked objects
		Order mockOrder = new Order ( 1, Lists.newArrayList() );
		
		Mockito.when(orderService.saveOrder( mockOrder ))
			.thenReturn( new Orders()  );
		
		// Execute the POST request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString ( mockOrder ) ))
				
				.andExpect (status().isBadRequest());
	}
	
	@Test
	@DisplayName("POST /api/v1/orders/id - Missing quantity should still works")
	final void testOrderItemMissingQuantityShouldWork() throws Exception {
		// Setup mocked objects
		OrderItem orderItem = new OrderItem( null, new MenuItem ( "Chips", "food" ) );
		Order mockOrder = new Order ( 1, Lists.newArrayList( orderItem ) );
		
		Mockito.when(orderService.saveOrder( any(Order.class) ))
			.thenReturn( new Orders()  );
		
		// Execute the POST request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString ( mockOrder ) ))
				
				.andExpect (status().isCreated());
	}
	
	@Test
	@DisplayName("POST /api/v1/orders/id - Missing menu item should throw bad request")
	final void testOrderItemMissingMenuItem() throws Exception {
		// Setup mocked objects
		
		ResponseStatusException e = assertThrows ( ResponseStatusException.class , () -> {
			OrderItem orderItem = new OrderItem( 1, new MenuItem ( "Chips", null ) );
			Order mockOrder = new Order ( 1, Lists.newArrayList( orderItem ) );
			
			assertEquals ( 1, mockOrder.getTableNumber() );
		});
		
		assertEquals ( e.getStatus().toString(), "400 BAD_REQUEST");
		assertTrue ( e.getMessage().contains("Missing") );
		
		e = assertThrows ( ResponseStatusException.class , () -> {
			OrderItem orderItem = new OrderItem( 1, new MenuItem ( null, "food" ) );
			Order mockOrder = new Order ( 1, Lists.newArrayList( orderItem ) );
			
			assertEquals( 1, mockOrder.getTableNumber() );
		});
		
		assertEquals ( e.getStatus().toString(), "400 BAD_REQUEST");
		assertTrue ( e.getMessage().contains("Missing") );
	}
	
	@Test
	@DisplayName("POST /api/v1/orders/id - Missing name or missing type in menu item should throw bad request")
	final void testOrderItemMissingMenuItemNameOrType() throws Exception {
		// Setup mocked objects
		
		ResponseStatusException e = assertThrows ( ResponseStatusException.class , () -> {
			OrderItem orderItem = new OrderItem( 1, new MenuItem ( null, "food" ) );
			Order mockOrder = new Order ( 1, Lists.newArrayList( orderItem ) );
			
			assertEquals( 1, mockOrder.getTableNumber() );
		});
		
		assertEquals ( e.getStatus().toString(), "400 BAD_REQUEST");
		assertTrue ( e.getMessage().contains("Missing") );
	}
	
	
	

	static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
