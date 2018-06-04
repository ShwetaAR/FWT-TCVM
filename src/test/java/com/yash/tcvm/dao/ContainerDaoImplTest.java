package com.yash.tcvm.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.yash.tcvm.daoimpl.ContainerDaoImpl;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.ContainerAlreadyUpdatedException;
import com.yash.tcvm.exception.ContainerCannotBeEmptyException;
import com.yash.tcvm.exception.ContainerExistException;
import com.yash.tcvm.exception.DoNotExistException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Container;

public class ContainerDaoImplTest {

	private ContainerDao containerDao;
	private int rowAffected;
	private Container container;

	@Before
	public void setUp() {
		containerDao = new ContainerDaoImpl();
	}

	@Test(expected = ContainerCannotBeEmptyException.class)
	public void addContainer_WhenEmptyContainerGiven_ShouldThrow() {
		ContainerDao containerDao = new ContainerDaoImpl();
		container = new Container();
		containerDao.insertContainer(container);
	}

	@Test(expected = NullPointerException.class)
	public void addContainer_WhenCalledaddContainerMethodWithNullConatiner_ShouldThrowNullPointerException() {
		ContainerDao containerDao = new ContainerDaoImpl();
		container = null;
		containerDao.insertContainer(container);
	}

	@Test(expected = ContainerExistException.class)
	public void addContainer_WhenCalledaddContainerMethodWithValidInput_ShouldAddContainerToJson() {
		containerDao.insertContainer(new Container(Ingredient.COFFEE, 8000, 8000));
	}

	@Test
	public void readAllContainer_WhenCalledgetContainersMethod_ShouldReturnAllTheContainersAvailable() {
		List<Container> listContainer = containerDao.readAllContainer();
		assertEquals(5, listContainer.size());
	}

	@Test(expected = EmptyException.class)
	public void readAllContainer_WhenContainersJsonFileIsEmpty_ThrowEmptyException() {
		containerDao.readAllContainer();

	}

	@Test(expected = NullPointerException.class)
	public void updateContainer_WhenNullContainerGiven_ShouldthrowNullPointerException() {
		ContainerDao containerDao = new ContainerDaoImpl();
		Container container = null;
		rowAffected = containerDao.updateContainer(container);

	}

	@Test
	public void updateContainer_WhenValidUpdateInputIsGiven_ShouldBeUpdatedInJson() {
		ContainerDao containerDao = new ContainerDaoImpl();
		rowAffected = containerDao.updateContainer(new Container(Ingredient.TEA, 2000, 1940));

	}

	@Test(expected = ContainerCannotBeEmptyException.class)
	public void updateContainer_WhenEmptyContainerGiven_ShouldthrowRunTimeException() {
		ContainerDao containerDao = new ContainerDaoImpl();
		Container container = new Container();
		rowAffected = containerDao.updateContainer(container);

	}

	@Test(expected = ContainerAlreadyUpdatedException.class)
	public void updateContainer_WhenThereIsNoChangeInUpdatedContainer_ThrowContainerAlreadyUpdatedException() {
		container = new Container(Ingredient.MILK, 10000, 9990.0);
		containerDao.updateContainer(container);
	}

	@Test
	public void readContainerByIngredient_WhenCalledgetContainersMethod_ShouldReturnContainerOfSpecifiedIngredients() {
		Container container = containerDao.readContainerByIngredient(Ingredient.SUGAR);
		assertEquals(Ingredient.SUGAR, container.getIngredient());

	}

	@Test(expected = NullPointerException.class)
	public void readContainerByIngredient_WhenGivenIgredientIsNull_ThrowNullPointerException() {
		Ingredient ingredient = null;
		containerDao.readContainerByIngredient(ingredient);

	}

	@Test(expected = DoNotExistException.class)
	public void readContainerByIngredient_WhenGivenIgredientDoNotExist_ThrowDoNotExistException() {
		containerDao.readContainerByIngredient(Ingredient.COFFEE);

	}

}
