package com.yash.tcvm.builder;

import java.util.Map;

import org.apache.log4j.Logger;

import com.yash.tcvm.configuration.AbstractBeverageConfigurer;
import com.yash.tcvm.configuration.BeverageConfiguration;
import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.daoimpl.ContainerDaoImpl;
import com.yash.tcvm.daoimpl.OrderDaoImpl;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.ContainerService;
import com.yash.tcvm.serviceimpl.ContainerServiceImpl;
import com.yash.tcvm.serviceimpl.OrderServiceImpl;

public abstract class AbstractBeverageBuilder implements BeverageBuilder {

	/**
	 * logger is used for logging and to write messages to the configured log files
	 */
	private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

	BeverageConfiguration beverageConfigurer;
	ContainerDao containerDao = new ContainerDaoImpl();
	ContainerService containerService = new ContainerServiceImpl(containerDao);

	public void setDrinkConfigurer(BeverageConfiguration beverageConfigurer) {
		this.beverageConfigurer = beverageConfigurer;
	}

	public void setContainerService(ContainerServiceImpl containerServiceImpl) {
		this.containerService = containerServiceImpl;
	}

	public boolean prepareDrink(Order order) {
		boolean ifUnderflow = checkUnderFlow(order);
		order.setStatus(true);
		boolean isUpdated = updateContainers(order, ifUnderflow);
		int rowAffected = insertOrder(order);
		if (rowAffected == 1 && ifUnderflow == true && isUpdated == true) {
			return true;
		} else
			return false;
	}

	private int insertOrder(Order order) {
		int rowAffected = 0;
		if (order.getStatus() == true) {
			OrderDao orderDao = new OrderDaoImpl();
			rowAffected = orderDao.updateOrder(order);
		}

		return rowAffected;
	}

	private boolean checkUnderFlow(Order order) {
		AbstractBeverageConfigurer abstractDrinkConfigurer = (AbstractBeverageConfigurer) beverageConfigurer;

		Map<Ingredient, Double> consumption = abstractDrinkConfigurer.getIngredientConsumption();
		Map<Ingredient, Double> wastage = abstractDrinkConfigurer.getIngredientWastage();
		boolean canBeUpdated = true;
		for (Map.Entry<Ingredient, Double> entry : consumption.entrySet()) {

			double qtyWasted = wastage.get(entry.getKey());
			double qtyConsumed = entry.getValue();
			double qtyAvailableInContainer = containerService.getContainerByIngredient(entry.getKey())
					.getCurrentAvailability();
			int noOfCups = order.getQuantity();

			if (isUnderFlowCondition(qtyWasted, qtyConsumed, qtyAvailableInContainer, noOfCups)) {
				canBeUpdated = false;
				try {
					throw new ContainerUnderflowException(entry.getKey() + "Insufficient");
				} catch (ContainerUnderflowException exception) {
					logger.error(entry.getKey()+"-----Container is Insufficient -----");
					logger.error("------Refill Container-----");

				}
			}
		}
		return canBeUpdated;

	}

	private boolean isUnderFlowCondition(double qtyWasted, double qtyConsumed, double qtyAvailableInContainer,
			int noOfCups) {
		return (noOfCups * (qtyConsumed + qtyWasted)) > qtyAvailableInContainer;
	}

	public boolean updateContainers(Order order, boolean canBeUpdated) {
		if (canBeUpdated == true) {
			AbstractBeverageConfigurer abstractDrinkConfigurer = (AbstractBeverageConfigurer) beverageConfigurer;

			Map<Ingredient, Double> consumption = abstractDrinkConfigurer.getIngredientConsumption();
			Map<Ingredient, Double> wastage = abstractDrinkConfigurer.getIngredientWastage();

			for (Map.Entry<Ingredient, Double> entry : consumption.entrySet()) {
				Container container = containerService.getContainerByIngredient(entry.getKey());
				double qtyWasted = wastage.get(entry.getKey());
				double qtyConsumed = entry.getValue();
				double qtyAvailableInContainer = container.getCurrentAvailability();
				int noOfCups = order.getQuantity();
				double currentAvailability = (qtyAvailableInContainer - (noOfCups * (qtyConsumed + qtyWasted)));
				container.setCurrentAvailability(qtyAvailableInContainer - (noOfCups * (qtyConsumed + qtyWasted)));
				containerService.updateContainer(container);

			}

		} else
			return !canBeUpdated;
		return canBeUpdated;
	}

}
