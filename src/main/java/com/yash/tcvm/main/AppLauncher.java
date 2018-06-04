package com.yash.tcvm.main;
/**
 * This class contain main method for start up
 * 
 * @author Shweta.baberia
 *
 */
public class AppLauncher {
	public static void main(String[] args) {
		TeaCofeeVendingMachine tcvm = new TeaCofeeVendingMachine();
		tcvm.displayMenu();
	}

}
