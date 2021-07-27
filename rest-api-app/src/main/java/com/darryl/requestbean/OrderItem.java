package com.darryl.requestbean;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItem {

	@JsonProperty("quantity")
	private Integer quantity = 1;
	
	private MenuItem menuItem;
	
	public OrderItem ( Integer quantity, MenuItem menuItem ) {
		super();
		this.setQuantity(quantity);	
		this.setMenuItem(menuItem);
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		if ( menuItem == null ) {
			throw new ResponseStatusException ( 
					HttpStatus.BAD_REQUEST, "Missing menu item");
		}
		this.menuItem = menuItem;
	}
	
	
}
