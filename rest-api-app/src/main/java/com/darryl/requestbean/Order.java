package com.darryl.requestbean;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class Order {

	@JsonProperty("tableNumber")
	@NotNull
	private Integer tableNumber;
	
	@NotEmpty
	List<OrderItem> orderItems;
	
	public Order() {
		super();
	}
	
	public Order ( Integer tableNumber, List<OrderItem> orderItems ) {
		this.setTableNumber(tableNumber);
		this.setOrderItems(orderItems);
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderItems, tableNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(orderItems, other.orderItems) && Objects.equals(tableNumber, other.tableNumber);
	}
	
	
}
