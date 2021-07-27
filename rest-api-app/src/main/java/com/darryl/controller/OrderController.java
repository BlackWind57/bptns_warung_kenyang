package com.darryl.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.darryl.bean.Orders;
import com.darryl.requestbean.Order;
import com.darryl.responsebean.OrderResponse;
import com.darryl.service.OrderService;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

	@Autowired
	private OrderService service;
	
	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponse>> getAllOrders() {
		try {
			return ResponseEntity.ok()
					.location( new URI("/orders") )
					.body(service.getAllOrders());
		} catch (URISyntaxException e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	@PostMapping("/orders")
	public ResponseEntity<Object> addOrder( 
			@Valid @RequestBody Order orders) {
		
		Orders savedOrder = null;
		try {
			savedOrder = service.saveOrder(orders);
		}
		catch ( ResponseStatusException e ) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getLocalizedMessage());
		}
		
		if ( savedOrder == null ) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("The server cannot process your request");
		}
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedOrder.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	
}
