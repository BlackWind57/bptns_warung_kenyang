package com.darryl.responsebean;

import java.util.List;
import java.util.stream.Collectors;

import com.darryl.bean.Orders;

public class OrderResponse {
	
	private Integer tableNumber;
	
	private List<OrderItemResponse> orderItems;

	public OrderResponse() {
		super();
	}
	
	public OrderResponse ( Orders orders ) {
		super();
		System.out.println ( orders.getTableNumber() );
		this.setTableNumber(orders.getTableNumber());
		this.setOrderItems(orders.getOrderItems()
			.stream()
			.map(OrderItemResponse::new)
			.collect(Collectors.toList()));
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public List<OrderItemResponse> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemResponse> orderItems) {
		this.orderItems = orderItems;
	}
}
