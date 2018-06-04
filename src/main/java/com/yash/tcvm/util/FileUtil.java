package com.yash.tcvm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.exception.EmptyFileException;
import com.yash.tcvm.exception.FileNotExistException;
import com.yash.tcvm.model.Container;

/**
 * 
 * @author Shweta.baberia
 *
 *         FileUtil class is used to readjson file and convertObjectToJson for
 *         all the modules. Its a common class for for all the modules.
 */
public class FileUtil {


	public static String readJsonFile(String filePath)throws EmptyException {
		File jsonFile = new File(filePath);
		// checkIfFileExist(jsonFile);
		 checkForEmptyFile(jsonFile);
		StringBuilder jsonBuilder = new StringBuilder();
		BufferedReader bufferedReader = generateBufferReaderFromjsonFile(jsonFile);
		readFromBufferedReader(jsonFile, jsonBuilder, bufferedReader);
		return jsonBuilder.toString();

	}

	private static void checkForEmptyFile(File jsonFile) {
		if(jsonFile.length() <= 0){
			throw new EmptyException("File is empty");
		}
		
	}

	private static void readFromBufferedReader(File jsonFile, StringBuilder jsonBuilder, BufferedReader bufferedReader) {
		String nextReadLine;
		try {
			nextReadLine = bufferedReader.readLine();
			while (isEndOfFile(nextReadLine)) {
				jsonBuilder.append(nextReadLine);
				nextReadLine = bufferedReader.readLine();
			}
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		}
	}

	private static boolean isEndOfFile(String readLine) {
		return readLine != null;
	}

	private static BufferedReader generateBufferReaderFromjsonFile(File jsonFile) {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(jsonFile));
			return bufferedReader;
		} catch (FileNotFoundException fileNotFoundException) {
		}
		return bufferedReader;
	}
	public static void convertObjectToJson(String json, String filepath) {

		try {
			FileWriter writer = new FileWriter(filepath);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void updateJsonValue(String json, String containerFilePath) {
	
		try {
			FileWriter writer = new FileWriter(containerFilePath);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
