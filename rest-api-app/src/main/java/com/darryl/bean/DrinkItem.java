package com.darryl.bean;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Drink_Item")
@DiscriminatorValue("Drinks")
public class DrinkItem extends MenuItem {
	
	public DrinkItem() {
		super();
	}
	
	public DrinkItem( String name, BigDecimal price ) {
		super();
		super.setName(name);
		super.setPrice(price);
	}
	
	@Override
	public String toString() {
		return "DrinkItem [name= " + super.getName() + ", "
				+ "price= " + super.getPriceInString() + "]";
	}
	
}
