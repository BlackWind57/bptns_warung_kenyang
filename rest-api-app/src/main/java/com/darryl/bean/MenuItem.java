package com.darryl.bean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotEmpty;
import javax.persistence.DiscriminatorType;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	discriminatorType = DiscriminatorType.STRING, 
	name = "Menu_Type")
public abstract class MenuItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@NotEmpty
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private BigDecimal price;

	public MenuItem() {
		super();
	}
	
	public MenuItem(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public String getPriceInString() {
		return "$" + price.toString();
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "MenuItem [name= " + name + ", price= " + getPriceInString() + "]";
	}
}
