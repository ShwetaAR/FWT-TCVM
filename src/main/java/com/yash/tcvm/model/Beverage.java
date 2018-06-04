package com.yash.tcvm.model;

public class Beverage {
	
	private int tea;
	private int coffee;
	private int sugar;
	private int milk;
	private int water;
	public Beverage() {
		super();
	}
	public Beverage(int tea, int coffee, int sugar, int milk, int water) {
		super();
		this.tea = tea;
		this.coffee = coffee;
		this.sugar = sugar;
		this.milk = milk;
		this.water = water;
	}
	public int getTea() {
		return tea;
	}
	public void setTea(int tea) {
		this.tea = tea;
	}
	public int getCoffee() {
		return coffee;
	}
	public void setCoffee(int coffee) {
		this.coffee = coffee;
	}
	public int getSugar() {
		return sugar;
	}
	public void setSugar(int sugar) {
		this.sugar = sugar;
	}
	public int getMilk() {
		return milk;
	}
	public void setMilk(int milk) {
		this.milk = milk;
	}
	public int getWater() {
		return water;
	}
	public void setWater(int water) {
		this.water = water;
	}
	
	 

}
