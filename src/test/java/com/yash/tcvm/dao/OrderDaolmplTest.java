package com.yash.tcvm.dao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.yash.tcvm.daoimpl.OrderDaoImpl;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.exception.InvalidQuantityException;
import com.yash.tcvm.model.Order;

public class OrderDaolmplTest {
	private OrderDao orderDao;
	private Order order;

	@Before
	public void setUp() {
		orderDao = new OrderDaoImpl();
	}

	@Test
	public void insertOrder_WhenValidInputGive_ShouldBeAddedToOrderJsonAndReturnOne() {
		int rowAffected = orderDao.insertOrder(new Order(2, BeverageType.TEA));
		System.out.println(rowAffected);

	}

	@Test
	public void getAllOrder_WhenCalledgetAllOrder_ShouldReturnListOfOrders() {
		List<Order> lisOfOrders = orderDao.getAllOrders();
		for (Order order : lisOfOrders) {
			System.out.println(order.getBeverageTypeEnum());
		}

	}

	@Test(expected = EmptyException.class)
	public void getAllOrders_WhenJSONFileForOrderIsEmpty_ShouldThrowException()
			throws FileNotFoundException, EmptyException {
		orderDao.getAllOrders();

	}

	@Test
	public void getOrders_WhenListOfOrderObjectAreInJSONFile_shouldReturnSizeOfOrdersList()
			throws FileNotFoundException, EmptyException {
		assertEquals(4, orderDao.getAllOrders().size());
	}

	@Test(expected = NullPointerException.class)
	public void insertOrder_WhenOrderObjectGivenIsNull_shouldThrowException()
			throws FileNotFoundException, EmptyException {
		order = null;
		assertEquals(2, orderDao.insertOrder(order));
	}

	@Test
	public void getOrdersByDrink_WhenBeverageTypeIsGivenAndItExistInJSONFile_ShouldReturnSizeOfOrderList()
			throws FileNotFoundException {
		assertEquals(1, orderDao.getOrdersByBeverage(BeverageType.TEA).size());
	}

	@Test
	public void getOrdersByBeverage_WhenBeverageTypeIsGivenAndJSONFileDoesntHasOrdersOfGivenBeverageType_ShouldReturnSizeOfOrderListAsZero()
			throws FileNotFoundException {
		assertEquals(0, orderDao.getOrdersByBeverage(BeverageType.BLACK_COFFEE).size());
	}

	@Test
	public void updateOrder_WhenOrderIsGiven_ReturnOneAndInGivenOrderQuantityIsUpdated() throws FileNotFoundException {
		int rowAffected = orderDao.updateOrder(new Order(4, BeverageType.TEA));
		assertEquals(1, rowAffected);
	}

	@Test
	public void updateOrder_WhenOrderIsGiven_InGivenOrderQuantityIsUpdated() throws FileNotFoundException {
		orderDao.updateOrder(new Order(4, BeverageType.TEA));
	}

	@Test(expected = InvalidQuantityException.class)
	public void updateOrder_WhenOrderQuantityIsZero_ThrowInvalidQuantityException() throws FileNotFoundException {
		orderDao.updateOrder(new Order(0, BeverageType.TEA));
	}

	@Test(expected = NullPointerException.class)
	public void updateOrder_WhenOrderQuantityIsNull_ThrowNullPointerException() throws FileNotFoundException {
		orderDao.updateOrder(order);
	}

	@Test
	public void updateOrder_WhenOrderOfSpecificBeverageDoNotExist_InsertThatOrderAndReturnOne()
			throws FileNotFoundException {
		int rowAffected = orderDao.updateOrder(new Order(4, BeverageType.BLACK_COFFEE));
		assertEquals(1, rowAffected);
	}

	@Test(expected = InvalidQuantityException.class)
	public void updateOrder_WhenOrderQuantityIsNegative_ThrowInvalidQuantityException() throws FileNotFoundException {
		orderDao.updateOrder(new Order(-8, BeverageType.TEA));
	}

	@Test(expected = NullPointerException.class)
	public void updateOrder_WhenOrderBeverageTypeIsNull_ThrowNullPointerException() throws FileNotFoundException {
		orderDao.updateOrder(new Order(8, null));
	}

}
