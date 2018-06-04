package com.yash.tcvm.builder;

import com.yash.tcvm.configuration.CoffeeConfiguration;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.model.Order;

public class CoffeeBuilder extends AbstractBeverageBuilder {

	public CoffeeBuilder() {
		setDrinkConfigurer(CoffeeConfiguration.getDrinkConfigurer());
	}

	@Override
	public boolean prepareDrink(Order order) throws ContainerUnderflowException {
		boolean prepareDrink=false;
		if (order.getBeverageTypeEnum() == BeverageType.COFFEE) {
			prepareDrink =super.prepareDrink(order);
		} else {
			throw new IllegalArgumentException(
					"Wrong Drink Type, required " + BeverageType.COFFEE + " and found " + order.getBeverageTypeEnum());
		}
		return prepareDrink;
	}

	public static BeverageBuilder getDrinkBuilder() {
		return new CoffeeBuilder();
	}

	

}
