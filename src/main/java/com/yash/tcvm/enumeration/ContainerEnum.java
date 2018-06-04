package com.yash.tcvm.enumeration;

public enum ContainerEnum {
	TEA(2000), SUGAR(8000), COFFEE(2000), MILK(10000), WATER(15000);

	private double maxCapacity;

	ContainerEnum(double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public double getMaxCapacity() {
		return maxCapacity;
	}

}
