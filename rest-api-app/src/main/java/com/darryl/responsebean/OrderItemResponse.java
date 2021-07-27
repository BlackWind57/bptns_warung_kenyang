package com.darryl.responsebean;

import com.darryl.bean.OrderItem;

public class OrderItemResponse {
	
	private Integer quantity;
	private String menuItem = "";
	
	public OrderItemResponse() {
		super();
	}

	public OrderItemResponse( OrderItem orderItem ) {
		super();
		this.setQuantity(orderItem.getQuantity());
		this.setMenuItem(orderItem.getMenuItem().toString());
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}
	
	
	
}
