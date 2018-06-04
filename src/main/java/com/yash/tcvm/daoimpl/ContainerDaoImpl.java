package com.yash.tcvm.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.yash.tcvm.constant.Constants;
import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.ContainerAlreadyUpdatedException;
import com.yash.tcvm.exception.ContainerCannotBeEmptyException;
import com.yash.tcvm.exception.ContainerExistException;
import com.yash.tcvm.exception.DoNotExistException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.util.FileUtil;

public class ContainerDaoImpl implements ContainerDao {

	private Gson gson;
	private String json;
	private List<Container> containerList = null;
	private Container selectedContainer;

	public List<Container> readAllContainer() throws EmptyException {
		containerList = new ArrayList<Container>();
		Container[] containerFromJsonFile = null;
		String jsonString1 = FileUtil.readJsonFile(Constants.CONTAINER_FILE_PATH);
		Gson gson = new Gson();
		containerFromJsonFile = gson.fromJson(jsonString1, Container[].class);
		containerList = new ArrayList<Container>(Arrays.asList(containerFromJsonFile));
		return containerList;
	}

	public Container readContainerByIngredient(Ingredient ingredient) throws DoNotExistException, NullPointerException {
		containerList = new ArrayList<Container>();
		containerList = readAllContainer();
		boolean ingredientExist = false;
		checkIfIngredientIsNull(ingredient);
		ingredientExist = getIngredient(ingredient, ingredientExist);
		checkIfIngredientExist(ingredientExist);
		return selectedContainer;
	}

	private void checkIfIngredientIsNull(Ingredient ingredient) {
		if (ingredient == null) {
			throw new NullPointerException("Ingredient Cannot be null");
		}
	}

	private boolean getIngredient(Ingredient ingredient, boolean ingredientExist) {
		for (Container container : containerList) {
			if (container.getIngredient() == ingredient) {
				selectedContainer = container;
				ingredientExist = true;
				break;
			}
		}
		return ingredientExist;
	}

	private void checkIfIngredientExist(boolean ingredientExist) {
		if (!ingredientExist) {
			throw new DoNotExistException("Container of this ingrdient donot exist");
		}
	}

	public int insertContainer(Container container) {
		gson = new Gson();
		int inserted = 1;
		checkIfContainerIsNull(container);
		checkIfContainerIsEmpty(container);
		containerList = readAllContainer();
		checkIfContainerAlreadyExist(container);
		addContainerToList(container);
		json = gson.toJson(containerList);
		FileUtil.convertObjectToJson(json, Constants.CONTAINER_FILE_PATH);
		return inserted;
	}

	private void addContainerToList(Container container) {
		if (containerList == null) {
			containerList = new ArrayList<Container>();
			containerList.add(container);
		} else
			containerList.add(container);
	}

	private void checkIfContainerAlreadyExist(Container container) {
		for (Container existingContainer : containerList) {
			if (existingContainer.getIngredient().equals(container.getIngredient())) {
				throw new ContainerExistException("Container Already exist Exception");
			}
		}
	}

	private void checkIfContainerIsEmpty(Container container) {
		if (container.getCurrentAvailability() == 0.0 && container.getIngredient() == null
				&& container.getMaxCapacity() == 0.0) {
			throw new ContainerCannotBeEmptyException("container cannot be Empty Set value");
		}
	}

	public int updateContainer(Container container)
			throws ContainerAlreadyUpdatedException, NullPointerException, ContainerCannotBeEmptyException {

		checkIfContainerIsNull(container);
		checkIfContainerIsEmpty(container);
		gson = new Gson();
		containerList = readAllContainer();
		Container[] containerFromJsonFile = null;
		int rowAffected = 0;
		for (Container existingContainer : containerList) {
			if (container.getIngredient().equals(existingContainer.getIngredient())) {
				existingContainer.setCurrentAvailability(container.getCurrentAvailability());
				rowAffected = 1;
				json = gson.toJson(containerList);
				FileUtil.convertObjectToJson(json, Constants.CONTAINER_FILE_PATH);
				return rowAffected;

			}
		}
		return rowAffected;
	}

	private void checkIfContainerIsNull(Container container) {
		if (container == null) {
			throw new NullPointerException("container cannot be null");
		}
	}

}
