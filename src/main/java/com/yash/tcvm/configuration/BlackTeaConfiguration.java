package com.yash.tcvm.configuration;

import java.util.HashMap;
import java.util.Map;

import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.enumeration.Ingredient;

/**
 * BlackTeaConfiguration class is used to configure BlackTea specifications,
 * here all the consumption needed to prepare Blacktea are specified, and
 * Configured tea wastage, tea used, rate of tea and type of beverage.
 * 
 * @author Shweta.baberia
 *
 */
public class BlackTeaConfiguration extends AbstractBeverageConfigurer {

	private static BeverageConfiguration beverageConfigurer;

	public static final double TEA_CONSUMPTION = 3;
	public static final double WATER_CONSUMPTION = 100;
	public static final double SUGAR_CONSUMPTION = 15;

	public static final double TEA_WASTAGE = 0;
	public static final double WATER_WASTAGE = 12;
	public static final double SUGAR_WASTAGE = 2;

	public static final double RATE = 5;

	/**
	 * 
	 * TeaConfiguration constructor
	 */
	private BlackTeaConfiguration() {
	}

	/**
	 * This static blocks is used to execute before constructor.
	 * 
	 */
	static {
		beverageConfigurer = new BlackTeaConfiguration();
	}

	public static BeverageConfiguration getDrinkConfigurer() {
		return beverageConfigurer;
	}

	public void ingredientConsumption() {
		Map<Ingredient, Double> ingredientsConsumption = new HashMap<Ingredient, Double>();
		ingredientsConsumption.put(Ingredient.TEA, TEA_CONSUMPTION);
		ingredientsConsumption.put(Ingredient.SUGAR, SUGAR_CONSUMPTION);
		ingredientsConsumption.put(Ingredient.WATER, WATER_CONSUMPTION);
		setIngredientConsumption(ingredientsConsumption);

	}

	public void ingredientWastage() {
		Map<Ingredient, Double> ingredientWastage = new HashMap<Ingredient, Double>();
		ingredientWastage.put(Ingredient.TEA, TEA_WASTAGE);
		ingredientWastage.put(Ingredient.SUGAR, SUGAR_WASTAGE);
		ingredientWastage.put(Ingredient.WATER, WATER_WASTAGE);
		setIngredientWastage(ingredientWastage);

	}

	public void beverageType() {
		setBeverageType(BeverageType.BLACK_TEA);

	}

	public void beverageRate() {
		setBeverageRate(RATE);

	}

}
