package com.yash.tcvm.dao;

import java.util.List;

import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.model.Order;

public interface OrderDao {

	int insertOrder(Order order);

	List<Order> getAllOrders();

	List<Order> getOrdersByBeverage(BeverageType beverageType);

	int  updateOrder(Order order);

}
