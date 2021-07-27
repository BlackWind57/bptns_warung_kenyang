package com.darryl.bean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//@Table(name="Food_Item")
@DiscriminatorValue("Food")
public class FoodItem extends MenuItem {
	
	@Column(name="dish_type", length=25)
	private String dishType;
	
	public FoodItem() {
		super();
	}
	
	public FoodItem( String name, BigDecimal price, String dishType ) {
		super();
		super.setName(name);
		super.setPrice(price);
		this.dishType = dishType;
	}

	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
	}

	@Override
	public String toString() {
		return "FoodItem [name= " + super.getName() + ", "
				+ "price= " + super.getPriceInString() + "]";
	}

}
