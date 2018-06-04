package com.yash.tcvm.configuration;

import java.util.HashMap;
import java.util.Map;

import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.enumeration.Ingredient;

/**
 * TeaConfiguration class is used to configure Tea specifications, here all the
 * consumption needed to prepare tea are specified,
 * and Configured tea wastage, tea used, rate of tea and type of beverage.
 * 
 * @author Shweta.baberia
 *
 */
public class TeaConfiguration extends AbstractBeverageConfigurer {

	private static BeverageConfiguration beverageConfigurer;

	public static final double WATER_CONSUMPTION = 60;
	public static final double SUGAR_CONSUMPTION = 15;
	public static final double MILK_CONSUMPTION = 40;
	public static final double TEA_CONSUMPTION = 5;

	public static final double WATER_WASTAGE = 5;
	public static final double SUGAR_WASTAGE = 2;
	public static final double MILK_WASTAGE = 4;
	public static final double TEA_WASTAGE = 1;

	public static final double RATE = 10;
	
	/**
	 * 
	 * TeaConfiguration constructor
	 */
	private TeaConfiguration() {
	}
	/**
	 * This static blocks is used to execute before constructor.
	 * 
	 */
	static {
		beverageConfigurer = new TeaConfiguration();
	}

	public static BeverageConfiguration getDrinkConfigurer() {
		return beverageConfigurer;
	}

	public void ingredientConsumption() {
		Map<Ingredient, Double> ingredientsConsumption = new HashMap<Ingredient, Double>();
		ingredientsConsumption.put(Ingredient.TEA, TEA_CONSUMPTION);
		ingredientsConsumption.put(Ingredient.SUGAR, SUGAR_CONSUMPTION);
		ingredientsConsumption.put(Ingredient.WATER, WATER_CONSUMPTION);
		ingredientsConsumption.put(Ingredient.MILK, MILK_WASTAGE);
		setIngredientConsumption(ingredientsConsumption);

	}

	public void ingredientWastage() {
		Map<Ingredient, Double> ingredientWastage = new HashMap<Ingredient, Double>();
		ingredientWastage.put(Ingredient.TEA, TEA_WASTAGE);
		ingredientWastage.put(Ingredient.SUGAR, SUGAR_WASTAGE);
		ingredientWastage.put(Ingredient.WATER, WATER_WASTAGE);
		ingredientWastage.put(Ingredient.MILK, MILK_WASTAGE);
		setIngredientWastage(ingredientWastage);

	}

	public void beverageType() {
		setBeverageType(BeverageType.TEA);

	}

	public void beverageRate() {
		setBeverageRate(RATE);

	}

}
