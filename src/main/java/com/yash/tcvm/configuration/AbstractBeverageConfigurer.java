package com.yash.tcvm.configuration;

import java.util.Map;

import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.enumeration.Ingredient;

public abstract class AbstractBeverageConfigurer implements BeverageConfiguration {

	private Map<Ingredient, Double> ingredientConsumption;

	private Map<Ingredient, Double> ingredientWastage;

	private double beverageRate;

	private BeverageType beverageType;

	public AbstractBeverageConfigurer() {
		initBeverageConfig();
	}

	private void initBeverageConfig() {

		ingredientConsumption();

		ingredientWastage();

		beverageType();

		beverageRate();

	}

	public Map<Ingredient, Double> getIngredientConsumption() {
		return ingredientConsumption;
	}

	public void setIngredientConsumption(Map<Ingredient, Double> ingredientConsumption) {
		this.ingredientConsumption = ingredientConsumption;
	}

	public Map<Ingredient, Double> getIngredientWastage() {
		return ingredientWastage;
	}

	public void setIngredientWastage(Map<Ingredient, Double> ingredientWastage) {
		this.ingredientWastage = ingredientWastage;
	}

	public double getBeverageRate() {
		return beverageRate;
	}

	public void setBeverageRate(double drinkRate) {
		this.beverageRate = drinkRate;
	}

	public BeverageType getBeverageType() {
		return beverageType;
	}

	public void setBeverageType(BeverageType beverageType) {
		this.beverageType = beverageType;
	}

	public static BeverageConfiguration getDrinkConfigurer() {
		// to override according to implementation
		return null;
	}

}
