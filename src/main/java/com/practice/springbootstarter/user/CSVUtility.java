package com.practice.springbootstarter.user;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVUtility {

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	/*
	 * public static void main(String[] args) { String filePath =
	 * "D:\\result.csv"; convertFileToCSV(filePath); }
	 */

	public static void convertFileToCSV(String filePath) {
		List<User> users = new ArrayList<>();
		users.add(new User(4, "Sanket", new Date()));
		users.add(new User(5, "Nikita", new Date()));
		users.add(new User(6, "Vivek", new Date()));

		File file = new File(filePath);
		try (FileWriter outputfile = new FileWriter(file)) {
			for (User user : users) {
				outputfile.append(String.valueOf(user.getId()));
				outputfile.append(COMMA_DELIMITER);
				outputfile.append(user.getName());
				outputfile.append(COMMA_DELIMITER);
				outputfile.append(user.getBdate().toString());
				outputfile.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("converted");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
