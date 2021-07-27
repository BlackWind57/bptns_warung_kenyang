package com.darryl.bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;

import com.darryl.enums.OrderStatus;


@Entity
public class Orders implements Cloneable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="uuid", nullable=false, length=16)
	private UUID uuid = UUID.randomUUID();
	
	@NotNull
	@Column(name="table_num", nullable=false)
	private Integer tableNumber;
	
	@Column(name="status", nullable=false, length=25)
	private String status = "PENDING";
	
	@Column(name="dateCreated", nullable=false)
	private Timestamp dateCreated = Timestamp.valueOf(LocalDateTime.now());
	
	@OneToMany( cascade = CascadeType.ALL )
	private List<OrderItem> orderItems;
	
	public Orders() {
		super();
	}
	
	public Orders( Integer tableNumber ) {
		super();
		this.setTableNumber(tableNumber);
		orderItems = new ArrayList<>();
	}
	
	public Orders ( Integer tableNumber, List<OrderItem> orderItems ) {
		super();
		this.setTableNumber(tableNumber);
		this.orderItems = new ArrayList<>();
		this.setOrderItems(orderItems);
	}
	
	public Orders ( Long id, Integer tableNumber, List<OrderItem> orderItems ) {
		super();
		this.setId(id);
		this.setTableNumber(tableNumber);
		this.orderItems = new ArrayList<>();
		this.setOrderItems(orderItems);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status.toString();
	}
	

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public List<OrderItem> getOrderItems() {
		List<OrderItem> listCopy = new ArrayList<>();
		for ( OrderItem item: this.orderItems ) {
			listCopy.add((OrderItem) item.clone());
		}
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		for ( OrderItem item: orderItems ) {
			this.orderItems.add( (OrderItem) item.clone() );
		}
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", uuid=" + uuid + ", tableNumber=" + tableNumber + ", status=" + status
				+ ", dateCreated=" + dateCreated + ", orderItems=" + orderItems + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateCreated, id, orderItems, status, tableNumber, uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orders other = (Orders) obj;
		return Objects.equals(dateCreated, other.dateCreated) && Objects.equals(id, other.id)
				&& Objects.equals(orderItems, other.orderItems) && Objects.equals(status, other.status)
				&& Objects.equals(tableNumber, other.tableNumber) && Objects.equals(uuid, other.uuid);
	}
	
	
	
}
