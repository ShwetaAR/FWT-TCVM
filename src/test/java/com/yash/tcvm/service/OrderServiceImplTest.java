package com.yash.tcvm.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.daoimpl.OrderDaoImpl;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.ContainerCannotBeEmptyException;
import com.yash.tcvm.exception.ContainerExistException;
import com.yash.tcvm.exception.DoNotExistException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.exception.InvalidQuantityException;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.serviceimpl.ContainerServiceImpl;
import com.yash.tcvm.serviceimpl.OrderServiceImpl;

@SuppressWarnings("unchecked")
public class OrderServiceImplTest {

	private Order order;
	private OrderDao orderDao;
	private OrderService orderService;
	private List<Order> listOfOrder;

	@Before
	public void setUp() {
		orderDao = mock(OrderDao.class);
		orderService = new OrderServiceImpl(orderDao);
	}

	@Test
	public void insertOrder_WhenValidInputGive_ShouldBeAddedToOrderJsonAndReturnOne() {
		order = new Order(4, BeverageType.TEA);
		when(orderDao.insertOrder(order)).thenReturn(1);
		int rowAffected = orderService.insertOrder(order);
		assertEquals(1, rowAffected);

	}

	@Test
	public void getAllOrder_WhenCalledgetAllOrder_ShouldReturnListOfOrders() {

		when(orderDao.getAllOrders()).thenReturn(Arrays.asList(new Order(1, BeverageType.BLACK_COFFEE)));
		listOfOrder = orderService.getAllOrders();
		assertEquals(1, listOfOrder.size());

	}

	@Test(expected = EmptyException.class)
	public void getAllOrders_WhenJSONFileForOrderIsEmpty_ShouldThrowException()
			throws FileNotFoundException, EmptyException {

		when(orderDao.getAllOrders()).thenThrow(EmptyException.class);
		orderService.getAllOrders();

	}

	@Test(expected = NullPointerException.class)
	public void insertOrder_WhenOrderObjectGivenIsNull_shouldThrowException()
			throws FileNotFoundException, EmptyException {
		order = null;
		when(orderDao.insertOrder(order)).thenThrow(NullPointerException.class);
		orderService.insertOrder(order);
	}

	@Test
	public void getOrdersByBeverage_WhenBeverageTypeIsGivenAndItExistInJSONFile_ShouldReturnSizeOfOrderList()
			throws FileNotFoundException {
		listOfOrder = new ArrayList<Order>();
		listOfOrder.add(new Order(1, BeverageType.TEA));
		when(orderDao.getOrdersByBeverage(BeverageType.TEA)).thenReturn(listOfOrder);

		assertEquals(listOfOrder, orderService.getOrdersByBeverage(BeverageType.TEA));
	}

	@Test
	public void getOrdersByBeverage_WhenBeverageTypeIsGivenAndJSONFileDoesntHasOrdersOfGivenBeverageType_ShouldReturnSizeOfOrderListAsZero()
			throws FileNotFoundException {
		listOfOrder = new ArrayList<Order>();
		when(orderDao.getOrdersByBeverage(BeverageType.TEA)).thenReturn(listOfOrder);
		assertEquals(0, orderDao.getOrdersByBeverage(BeverageType.BLACK_COFFEE).size());
	}

	@Test
	public void updateOrder_WhenOrderIsGiven_ReturnOneAndInGivenOrderQuantityIsUpdated() throws FileNotFoundException {
		order = new Order(1, BeverageType.TEA);
		when(orderDao.updateOrder(order)).thenReturn(1);
		int rowAffected = orderService.updateOrder(order);
		assertEquals(1, rowAffected);
	}

	@Test(expected = InvalidQuantityException.class)
	public void updateOrder_WhenOrderQuantityIsZero_ThrowInvalidQuantityException() throws FileNotFoundException {
		order = new Order(0, BeverageType.TEA);
		when(orderDao.updateOrder(order)).thenThrow(InvalidQuantityException.class);
		orderService.updateOrder(order);

	}

	@Test(expected = NullPointerException.class)
	public void updateOrder_WhenOrderQuantityIsNull_ThrowNullPointerException() throws FileNotFoundException {
		order = null;
		when(orderDao.updateOrder(order)).thenThrow(NullPointerException.class);
		orderService.updateOrder(order);

	}

	@Test
	public void updateOrder_WhenOrderOfSpecificBeverageDoNotExist_InsertThatOrderAndReturnOne()
			throws FileNotFoundException {
		order = new Order(4, BeverageType.BLACK_COFFEE);
		when(orderDao.updateOrder(order)).thenReturn(1);
		int rowAffected = orderService.updateOrder(order);
		assertEquals(1, rowAffected);
	}

	@Test(expected = InvalidQuantityException.class)
	public void updateOrder_WhenOrderQuantityIsNegative_ThrowInvalidQuantityException() throws FileNotFoundException {
		
		order = new Order(-8, BeverageType.TEA);
		when(orderDao.updateOrder(order)).thenThrow(InvalidQuantityException.class);
		orderService.updateOrder(order);
		
	}

	@Test(expected = NullPointerException.class)
	public void updateOrder_WhenOrderBeverageTypeIsNull_ThrowNullPointerException() throws FileNotFoundException {
		order = new Order(8, null);
		when(orderDao.updateOrder(order)).thenThrow(NullPointerException.class);
		orderService.updateOrder(order);
	
	}

}
