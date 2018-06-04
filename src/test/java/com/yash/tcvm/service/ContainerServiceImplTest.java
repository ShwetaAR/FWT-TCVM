package com.yash.tcvm.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.ContainerCannotBeEmptyException;
import com.yash.tcvm.exception.ContainerExistException;
import com.yash.tcvm.exception.DoNotExistException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.serviceimpl.ContainerServiceImpl;

@SuppressWarnings("unchecked")
public class ContainerServiceImplTest {

	private Container container;
	private ContainerDao containerDao;
	private ContainerService containerService;
	private List<Container> listOfContainer;

	@Before
	public void setUp() {
		containerDao = mock(ContainerDao.class);
		containerService = new ContainerServiceImpl(containerDao);

	}

	@Test
	public void getContainers_WhenCalledgetContainersMethod_ShouldReturnAllTheContainersAvailable() {
		getDaoMockedContainer(containerDao);
		listOfContainer = containerService.getContainers();
		assertEquals(3, listOfContainer.size());

	}

	@Test(expected = EmptyException.class)
	public void getContainers_WhenContainersJsonFileIsEmpty_ThrowEmptyException() {

		when(containerDao.readAllContainer()).thenThrow(EmptyException.class);
		containerService.getContainers();

	}

	@Test
	public void getContainerByIngredient_WhenCalledgetContainersMethod_ShouldReturnContainerOfSpecifiedIngredients() {
		when(containerDao.readContainerByIngredient(Ingredient.TEA))
				.thenReturn(new Container(Ingredient.TEA, 2000.0, 1925.0));
		Container container = containerService.getContainerByIngredient(Ingredient.TEA);
		assertEquals(Ingredient.TEA, container.getIngredient());

	}

	@Test(expected = NullPointerException.class)
	public void getContainerByIngredient_WhenGivenIgredientIsNull_ThrowNullPointerException() {
		when(containerDao.readContainerByIngredient(null)).thenThrow(NullPointerException.class);
		containerService.getContainerByIngredient(null);

	}

	@Test(expected = DoNotExistException.class)
	public void getContainerByIngredient_WhenGivenIgredientDoNotExist_ThrowDoNotExistException() {

		when(containerDao.readContainerByIngredient(Ingredient.COFFEE)).thenThrow(DoNotExistException.class);
		containerService.getContainerByIngredient(Ingredient.COFFEE);

	}

	private void getDaoMockedContainer(ContainerDao containerDao) {
		when(containerDao.readAllContainer()).thenReturn(Arrays.asList(new Container(Ingredient.MILK, 10000, 10000),
				new Container(Ingredient.TEA, 2000, 2000), new Container(Ingredient.WATER, 15000, 15000)));
	}

	@Test
	public void addContainer_WhenValidInputIsGiven_ShouldBeAddedToJsonFile() {
		container = new Container(Ingredient.WATER, 15000, 15000);
		when(containerDao.insertContainer(container)).thenReturn(1);
		int rowAffected = containerService.addContainer(container);
		assertEquals(1, rowAffected);

	}

	@Test(expected = NullPointerException.class)
	public void addContainer_WhenContainerObjectIsNull_ThrowNullPointerException() {
		container = null;
		when(containerDao.insertContainer(container)).thenThrow(NullPointerException.class);
		containerService.addContainer(container);

	}

	@Test(expected = ContainerCannotBeEmptyException.class)
	public void addContainer_WhenContainerObjectIsEmpty_ThrowContainerCannotBeEmptyException() {
		container = new Container();
		when(containerDao.insertContainer(container)).thenThrow(ContainerCannotBeEmptyException.class);
		containerService.addContainer(container);

	}

	@Test(expected = ContainerExistException.class)
	public void addContainer_WhenContainerObjectAlreadyExist_ThrowContainerExistException() {
		container = new Container(Ingredient.TEA, 2000, 2000);
		when(containerDao.insertContainer(container)).thenThrow(ContainerExistException.class);
		containerService.addContainer(container);

	}

	@Test(expected = ContainerCannotBeEmptyException.class)
	public void updateContainer_WhenContainerObjectIsEmpty_ThrowContainerCannotBeEmptyException() {
		container = new Container(null, 0.0, 0.0);
		when(containerDao.updateContainer(container)).thenThrow(ContainerCannotBeEmptyException.class);
		containerService.updateContainer(container);

	}

	@Test(expected = NullPointerException.class)
	public void updateContainer_WhenContainerObjectIsNull_ThrowNullPointerException() {
		container = null;
		when(containerDao.updateContainer(container)).thenThrow(NullPointerException.class);
		containerService.updateContainer(container);

	}

	@Test
	public void updateContainer_WhenValidUpdateInputIsGiven_ShouldBeUpdatedInJsonAndReturnOne() {
		container = new Container(Ingredient.MILK, 10000, 9950.0);
		when(containerDao.updateContainer(container)).thenReturn(1);
		int rowAffected = containerService.updateContainer(container);
		assertEquals(1, rowAffected);

	}

}
