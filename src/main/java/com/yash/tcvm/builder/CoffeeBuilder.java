package com.yash.tcvm.builder;

import org.apache.log4j.Logger;

import com.yash.tcvm.configuration.CoffeeConfiguration;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.serviceimpl.OrderServiceImpl;

public class CoffeeBuilder extends AbstractBeverageBuilder {

	/**
	 * logger is used for logging and to write messages to the configured log files
	 */
	private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

	public CoffeeBuilder() {
		setDrinkConfigurer(CoffeeConfiguration.getDrinkConfigurer());
	}

	@Override
	public boolean prepareDrink(Order order) throws ContainerUnderflowException {

		boolean prepareDrink = false;
		if (order.getBeverageTypeEnum() == BeverageType.COFFEE) {
			prepareDrink = super.prepareDrink(order);
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
