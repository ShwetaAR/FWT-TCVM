package com.yash.tcvm.serviceimpl;

import java.util.List;

import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.daoimpl.OrderDaoImpl;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.OrderService;

public class OrderServiceImpl implements OrderService {

	private OrderDao orderDao;

	public OrderServiceImpl() {
		orderDao = new OrderDaoImpl();
	}

	public OrderServiceImpl(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public int insertOrder(Order order) {
		return orderDao.insertOrder(order);
	}

	public List<Order> getAllOrders() {
		return orderDao.getAllOrders();
	}

	public List<Order> getOrdersByBeverage(BeverageType beverageType) {
		return orderDao.getOrdersByBeverage(beverageType);
	}

	public int updateOrder(Order order) {
		return orderDao.updateOrder(order);
	}

}
