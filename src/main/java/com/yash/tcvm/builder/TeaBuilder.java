package com.yash.tcvm.builder;

import com.yash.tcvm.configuration.TeaConfiguration;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.model.Order;

public class TeaBuilder extends AbstractBeverageBuilder {

	public TeaBuilder() {
		setDrinkConfigurer(TeaConfiguration.getDrinkConfigurer());
	}

	@Override
	public boolean prepareDrink(Order order) throws ContainerUnderflowException {
		boolean prepareDrink=false;
		if (order.getBeverageTypeEnum() == BeverageType.TEA) {
			prepareDrink =super.prepareDrink(order);
		} else {
			throw new IllegalArgumentException(
					"Wrong Drink Type, required " + BeverageType.TEA + " and found " + order.getBeverageTypeEnum());
		}
		return prepareDrink;
	}

	public static BeverageBuilder getDrinkBuilder() {
		return new TeaBuilder();
	}

	

}
