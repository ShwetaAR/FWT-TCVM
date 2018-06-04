package com.yash.tcvm.menu;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.yash.tcvm.exception.EmptyFileException;
import com.yash.tcvm.exception.FileNotExistException;
import com.yash.tcvm.main.TeaCofeeVendingMachine;

public class TCVMTest {
	private File filePath;
	private String filename;
	private TeaCofeeVendingMachine tcvm;

	@Before
	public void setUp() {
		tcvm = new TeaCofeeVendingMachine();
	}

	@Test(expected = FileNotExistException.class)
	public void getMenu_ThrowFileGivenNotFoundException_WhenFileGivenDonotExist() throws IOException {

		filename = "tcvmMenuNotFoundFile";
		filePath = new File("src/main/resources/menu/" + filename + ".txt");
		tcvm.getMenu(filePath);

	}

	@Test(expected = EmptyFileException.class)
	public void getMenu_ThrowFileIsEmptyException_WhenFileIsEmpty() throws IOException {
		String filename = "tcvmMenuTest";
		filePath = new File("src/test/resources/menuTest/" + filename + ".txt");
		tcvm.getMenu(filePath);
	}

	@Test(expected = NullPointerException.class)
	public void getMenu_ThrowNullPointerException_WhenFileGivenIsNull() throws IOException {
		tcvm.getMenu(filePath);

	}

}
