package com.yash.tcvm.service;

import java.util.List;

import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.model.Container;

public interface ContainerService {
	
	public Container getContainerByIngredient(Ingredient ingredient);
	
	public List<Container> getContainers();

	public int addContainer(Container container);

	public int updateContainer(Container container);

}
