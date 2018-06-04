package com.yash.tcvm.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.yash.tcvm.constant.Constants;
import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.exception.InvalidQuantityException;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.util.FileUtil;

public class OrderDaoImpl implements OrderDao {
	private Gson gson;
	private String json;
	private List<Order> orderList;

	public OrderDaoImpl() {
		gson = new Gson();
	}

	public int insertOrder(Order order) {
		orderList = null;
		int inserted = 0;
		checkIfOrderIsNull(order);
		orderList = getAllOrders();
		inserted = checkIfOrderWithSameIngredientExist(order, inserted);
		if (inserted == 1) {
			return inserted;
		} else {

			if (orderList == null) {
				orderList = new ArrayList<Order>();
				orderList.add(order);
				inserted = 1;
			} else {
				inserted = 1;
				orderList.add(order);
			}

			json = gson.toJson(orderList);
			FileUtil.convertObjectToJson(json, Constants.ORDER_FILE_PATH);
		}
		return inserted;

	}

	private int checkIfOrderWithSameIngredientExist(Order order, int inserted) {
		for (Order existingOrders : orderList) {
			if (existingOrders.getBeverageTypeEnum().equals(order.getBeverageTypeEnum())) {
				updateOrder(order);
				inserted = 1;
				return inserted;
			}
		}
		return inserted;
	}

	public List<Order> getAllOrders() throws EmptyException {
		orderList = new ArrayList<Order>();
		Order[] orderFromJsonFile = null;
		String jsonString1 = FileUtil.readJsonFile(Constants.ORDER_FILE_PATH);
		Gson gson = new Gson();
		orderFromJsonFile = gson.fromJson(jsonString1, Order[].class);
		orderList = new ArrayList<Order>(Arrays.asList(orderFromJsonFile));
		if (orderList.isEmpty()) {
			throw new EmptyException("There's No Order");
		}
		return orderList;
	}

	public List<Order> getOrdersByBeverage(BeverageType beverageType) {
		List<Order> selectedOrders = new ArrayList<Order>();
		orderList = getAllOrders();
		if (orderList != null || orderList.size() > 0) {
			for (Order order : orderList) {
				if (order.getBeverageTypeEnum() == beverageType) {
					selectedOrders.add(order);
				}
			}
		}
		return selectedOrders;
	}

	public int updateOrder(Order order) throws InvalidQuantityException, NullPointerException {
		checkIfOrderQuantityIsZero(order);
		checkForNegativeQuantity(order);
		checkIfIngredientIsNull(order);
		List<Order> orderByBeverage;
		orderByBeverage = getOrdersByBeverage((order.getBeverageTypeEnum()));
		orderList = getAllOrders();
		checkIfOrderIsNull(order);
		int rowAffected = 0;
		if (orderByBeverage.isEmpty()) {
			rowAffected = insertOrder(order);
		} else {
			rowAffected = calculateOrderQuantity(order, rowAffected);
		}
		return rowAffected;
	}

	private void checkIfIngredientIsNull(Order order) {
		if (order.getBeverageTypeEnum() == null) {
			throw new NullPointerException("Ingredient Cannot Be null");
		}
	}

	private void checkForNegativeQuantity(Order order) {
		if (order.getQuantity() < 0) {
			throw new InvalidQuantityException("Quantity cannot be negative");
		}
	}

	private int calculateOrderQuantity(Order order, int rowAffected) {
		int newQuantity;
		for (Order existingOrder : orderList) {
			if (existingOrder.getBeverageTypeEnum().equals(order.getBeverageTypeEnum())) {
				newQuantity = existingOrder.getQuantity() + order.getQuantity();
				existingOrder.setQuantity(newQuantity);
				json = gson.toJson(orderList);
				rowAffected = 1;
				FileUtil.convertObjectToJson(json, Constants.ORDER_FILE_PATH);
			}
		}
		return rowAffected;
	}

	private void checkIfOrderIsNull(Order order) {
		if (order == null) {
			throw new NullPointerException("Order cannot be null");
		}
	}

	private void checkIfOrderQuantityIsZero(Order order) {
		if (order.getQuantity() == 0) {
			throw new InvalidQuantityException("Order Cannot be Zero");
		}
	}

}
