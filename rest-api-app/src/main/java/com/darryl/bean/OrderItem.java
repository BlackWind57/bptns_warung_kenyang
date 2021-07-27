package com.darryl.bean;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class OrderItem implements Cloneable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="uuid", nullable=false, length=16)
	private UUID uuid = UUID.randomUUID();
	
	@NotNull
	@Column(name="quantity", nullable=false)
	private Integer quantity = 1;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="orders_id", insertable=false,updatable=false)
	private Orders orders;
	
	@ManyToOne (cascade=CascadeType.ALL)
	private MenuItem menuItem;
	
	public OrderItem() {
		super();
	}

	public OrderItem( Integer quantity, 
				MenuItem menuItem, 
				Orders orders ) {
		super();
		this.setQuantity(quantity);
		this.setMenuItem(menuItem);
		this.setOrders(orders);
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public Long getId() {
		return id;
	}

	@Override
	protected Object clone() {
		try {
			return (OrderItem) super.clone();
		} catch ( CloneNotSupportedException e ) {
			return new OrderItem (
					this.quantity, 
					this.menuItem, 
					this.orders);
		}
	}

	@Override
	public String toString() {
		return "OrderItem [quantity=" + quantity + ", orders=" + orders.getId() + ", menuItem=" + menuItem + "]";
	}
	
	
}
