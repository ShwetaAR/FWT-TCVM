package com.yash.tcvm.dao;

import java.util.List;

import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.model.Container;

public interface ContainerDao {

	List<Container> readAllContainer();

	Container readContainerByIngredient(Ingredient ingredient);

	int insertContainer(Container container);

	int updateContainer(Container container);

}
