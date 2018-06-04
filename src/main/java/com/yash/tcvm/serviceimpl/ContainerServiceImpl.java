package com.yash.tcvm.serviceimpl;

import java.util.List;

import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.daoimpl.ContainerDaoImpl;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.ContainerCannotBeEmptyException;
import com.yash.tcvm.exception.ContainerExistException;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.service.ContainerService;

public class ContainerServiceImpl implements ContainerService {

	private List<Container> containers;
	private ContainerDao containerDao;
	private Container selectedContainer;

	public ContainerServiceImpl(ContainerDao containerDao) {
		this.containerDao = containerDao;
	}

	/*public ContainerServiceImpl() {
		this.containerDao=  new ContainerDaoImpl();
	}*/

	public Container getContainerByIngredient(Ingredient ingredient) {
		selectedContainer = containerDao.readContainerByIngredient(ingredient);
		return selectedContainer;
	}

	public List<Container> getContainers() {
		return containerDao.readAllContainer();
	}

	public int addContainer(Container container) {
		boolean containerExist = false;
		checkIfContainerIsNull(container);
		checkIfContainerEmpty(container);
		checkIfContainerExist(container, containerExist);
		return containerDao.insertContainer(container);
	}

	private void checkIfContainerExist(Container container, boolean containerExist) {
		containers = containerDao.readAllContainer();
		for (Container existingContainer : containers) {
			if (container.equals(existingContainer)) {
				System.out.println(container.getIngredient() + "Already Exist");
				containerExist = true;
				throw new ContainerExistException("Container exist");

			}
		}
	}

	private void checkIfContainerIsNull(Container container) {
		if (container == null) {
			throw new NullPointerException("Container cannot be Null Exception");
		}
	}

	private void checkIfContainerEmpty(Container container) {
		if (container == null) {
			throw new ContainerCannotBeEmptyException("Container cannot be Empty Exception");
		}
	}

	public int updateContainer(Container container) {
		return containerDao.updateContainer(container);
	}

}
