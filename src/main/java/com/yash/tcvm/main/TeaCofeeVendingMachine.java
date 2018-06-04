package com.yash.tcvm.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yash.tcvm.builder.BeverageBuilder;
import com.yash.tcvm.builder.BlackCoffeeBuilder;
import com.yash.tcvm.builder.BlackTeaBuilder;
import com.yash.tcvm.builder.CoffeeBuilder;
import com.yash.tcvm.builder.TeaBuilder;
import com.yash.tcvm.configuration.BlackCoffeeConfiguration;
import com.yash.tcvm.configuration.BlackTeaConfiguration;
import com.yash.tcvm.configuration.CoffeeConfiguration;
import com.yash.tcvm.configuration.TeaConfiguration;
import com.yash.tcvm.constant.Constants;
import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.daoimpl.ContainerDaoImpl;
import com.yash.tcvm.enumeration.BeverageType;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.EmptyFileException;
import com.yash.tcvm.exception.FileNotExistException;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.ContainerService;
import com.yash.tcvm.service.OrderService;
import com.yash.tcvm.serviceimpl.ContainerServiceImpl;
import com.yash.tcvm.serviceimpl.OrderServiceImpl;

public class TeaCofeeVendingMachine {

	private Scanner scanner;
	private static BufferedReader br;
	private ContainerService containerService;
	private OrderService orderService;
	private ContainerDao containerDao;
	private List<Container> listOfContainer;

	public TeaCofeeVendingMachine() {
		scanner = new Scanner(System.in);
		containerDao = new ContainerDaoImpl();
		containerService = new ContainerServiceImpl(containerDao);
		orderService = new OrderServiceImpl();
	}

	public void getMenu(File filePath) {
		checkIfFileIsNull(filePath);
		checkIfFileExist(filePath);
		checkIfFileIsEmpty(filePath);
		try {
			br = new BufferedReader(new FileReader(filePath));
			String st;
			while ((st = br.readLine()) != null)
				System.out.println(st);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void checkIfFileIsEmpty(File file) {
		if (file.exists() && file.length() == 0) {
			throw new EmptyFileException("Given file is empty ");
		}
	}

	private void checkIfFileExist(File file) {
		if (!file.exists()) {
			throw new FileNotExistException("Given file donot exist in the given directory ");
		}
	}

	private void checkIfFileIsNull(File filePath) {
		if (filePath == null) {
			throw new NullPointerException("File cannot be null, assign some value to it");
		}
	}

	public void displayMenu() {
		String choice;
		do {
			File file = new File(Constants.TCVM_MENU_FILE_PATH);
			getMenu(file);

			int key = scanner.nextInt();
			switch (key) {
			case 1:
				makeCoffee(BeverageType.COFFEE, CoffeeBuilder.getDrinkBuilder());
				break;
			case 2:
				makeTea(BeverageType.TEA, TeaBuilder.getDrinkBuilder());
				break;
			case 3:
				makeBlackCofeee(BeverageType.BLACK_COFFEE, BlackCoffeeBuilder.getDrinkBuilder());
				break;
			case 4:
				makeBlackTea(BeverageType.BLACK_TEA, BlackTeaBuilder.getDrinkBuilder());
				break;
			case 5:
				refillContainer();
				break;
			case 6:
				checkTotalSale();
				break;
			case 7:
				containerStatus();
				break;
			case 8:
				reportGeneration();
				break;
			default:
				System.out.println("Invalid Choice");
			}
			System.out.println("Do you want continue?Y/N");
			choice = scanner.next();
		} while (choice.equalsIgnoreCase("y"));

	}

	private void reportGeneration() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			List<Order> listOfOrders = orderService.getAllOrders();
			XSSFSheet sheet = workbook.createSheet("sheet1");
			int rownum = 0;
			for (Order user : listOfOrders) {
				Row row = sheet.createRow(rownum++);
				createList(user, row);
			}
			FileOutputStream out = new FileOutputStream(new File("src/main/resources/excelFiles/report.xlsx"));
			workbook.write(out);
			out.close();

		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	private void createList(Order order, Row row) {
		double sale = 0;
		sale = checkForBeverageTypeAndItsTotalSale(sale, order);

		Cell cell = row.createCell(0);
		cell.setCellValue(order.getBeverageTypeEnum().toString());

		cell = row.createCell(1);
		cell.setCellValue(order.getQuantity());

		cell = row.createCell(2);
		cell.setCellValue(sale);

	}

	private void containerStatus() {
		listOfContainer = containerService.getContainers();
		for (Container container : listOfContainer) {
			displayContainersStatus(container);
		}

	}

	private void displayContainersStatus(Container container) {
		if (container.getIngredient().equals(Ingredient.WATER) || container.getIngredient().equals(Ingredient.MILK)) {
			System.out.println("-" + container.getIngredient() + ":" + container.getCurrentAvailability() + " ml");
		} else
			System.out.println("-" + container.getIngredient() + ":" + container.getCurrentAvailability() + " gm");
	}

	private void checkTotalSale() {

		List<Order> listOfOrders = orderService.getAllOrders();
		System.out.printf("%10s %30s %20s ", "Beverage Type", "Quantity Sold", "TotalSale");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------");
		calculateBeverageRates(listOfOrders);
		System.out.println("-----------------------------------------------------------------------------");
	}

	private void calculateBeverageRates(List<Order> listOfOrders) {
		double sale = 0;
		for (Order order : listOfOrders) {
			sale = checkForBeverageTypeAndItsTotalSale(sale, order);

			System.out.format("%10s %30s %20s", order.getBeverageTypeEnum(), order.getQuantity(), sale);
			System.out.println();
		}
	}

	private double checkForBeverageTypeAndItsTotalSale(double sale, Order order) {
		if (order.getBeverageTypeEnum().equals(BeverageType.TEA)) {

			sale = order.getQuantity() * TeaConfiguration.RATE;
		}
		if (order.getBeverageTypeEnum().equals(BeverageType.COFFEE)) {
			sale = order.getQuantity() * CoffeeConfiguration.RATE;
		}
		if (order.getBeverageTypeEnum().equals(BeverageType.BLACK_COFFEE)) {
			sale = order.getQuantity() * BlackCoffeeConfiguration.RATE;
		}
		if (order.getBeverageTypeEnum().equals(BeverageType.BLACK_TEA)) {
			sale = order.getQuantity() * BlackTeaConfiguration.RATE;
		}
		return sale;
	}

	private void refillContainer() {
		listOfContainer = containerService.getContainers();
		int rowAffected = 0;
		rowAffected = seContainerCapacity(listOfContainer, rowAffected);
		if (rowAffected == 1) {
			System.out.println("Containers Are Refilled");
		}
	}

	private int seContainerCapacity(List<Container> listOfContainer, int rowAffected) {
		for (Container container : listOfContainer) {
			double amoutOfIngredientToRefill = container.getMaxCapacity() - container.getCurrentAvailability();
			container.setCurrentAvailability(amoutOfIngredientToRefill + container.getCurrentAvailability());
			rowAffected = containerService.updateContainer(container);
		}
		return rowAffected;
	}

	private void makeBlackTea(BeverageType blackTea, BeverageBuilder beverageBuilder) {
		takeUserInput(blackTea, beverageBuilder);

	}

	private void makeBlackCofeee(BeverageType blackCoffee, BeverageBuilder beverageBuilder) {
		takeUserInput(blackCoffee, beverageBuilder);

	}

	private void makeTea(BeverageType tea, BeverageBuilder beverageBuilder) {
		takeUserInput(tea, beverageBuilder);

	}

	private void makeCoffee(BeverageType coffee, BeverageBuilder beverageBuilder) {
		takeUserInput(coffee, beverageBuilder);

	}

	private void takeUserInput(BeverageType beverageType, BeverageBuilder beverageBuilder) {
		System.out.println("Enter number of cups " + beverageType + " to be served");
		int orderQuantity = scanner.nextInt();
		BeverageBuilder BulidbeverageBuilder = beverageBuilder;
		boolean isDrinkPrepared = BulidbeverageBuilder.prepareDrink(new Order(orderQuantity, beverageType));
		if (isDrinkPrepared == true) {
			System.out.println(orderQuantity + " cups" + beverageType + "  prepared");
		} else
			System.out.println("Try Again");

	}

}
