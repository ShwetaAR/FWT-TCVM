package com.yash.tcvm.builder;

import org.apache.log4j.Logger;

import com.yash.tcvm.configuration.CoffeeConfiguration;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.serviceimpl.OrderServiceImpl;

public class BlackCoffeeBuilder extends AbstractBeverageBuilder {
	

	/**
	 * logger is used for logging and to write messages to the configured log files
	 */
	private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

	public BlackCoffeeBuilder() {
		setDrinkConfigurer(CoffeeConfiguration.getDrinkConfigurer());
	}

	@Override
	public boolean prepareDrink(Order order) throws ContainerUnderflowException {
		logger.info("prepareDrink(Order order) Of BlackCoffeeBuilder called");
		boolean prepareDrink=false;
		if (order.getBeverageTypeEnum() == BeverageType.BLACK_COFFEE) {
			prepareDrink =super.prepareDrink(order);
		} else {
			throw new IllegalArgumentException(
					"Wrong Drink Type, required " + BeverageType.BLACK_COFFEE + " and found " + order.getBeverageTypeEnum());
		}
		return prepareDrink;
	}

	public static BeverageBuilder getDrinkBuilder() {
		return new BlackCoffeeBuilder();
	}

	

}
