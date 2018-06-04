package com.yash.tcvm.builder;

import com.yash.tcvm.configuration.BlackTeaConfiguration;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.model.Order;

public class BlackTeaBuilder extends AbstractBeverageBuilder {

	public BlackTeaBuilder() {
		setDrinkConfigurer(BlackTeaConfiguration.getDrinkConfigurer());
	}

	@Override
	public boolean prepareDrink(Order order) throws ContainerUnderflowException {
		boolean prepareDrink=false;
		if (order.getBeverageTypeEnum() == BeverageType.BLACK_TEA) {
			prepareDrink =super.prepareDrink(order);
		} else {
			throw new IllegalArgumentException(
					"Wrong Drink Type, required " + BeverageType.BLACK_TEA + " and found " + order.getBeverageTypeEnum());
		}
		return prepareDrink;
	}

	public static BeverageBuilder getDrinkBuilder() {
		return new BlackTeaBuilder();
	}

	

}
