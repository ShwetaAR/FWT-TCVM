package com.yash.tcvm.builder;

import com.yash.tcvm.configuration.BeverageConfiguration;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.model.Order;

public interface BeverageBuilder {

	void setDrinkConfigurer(BeverageConfiguration beverageConfigurer);

	boolean prepareDrink(Order order) throws ContainerUnderflowException;

	boolean updateContainers(Order order,boolean canBeUpdated);
}
