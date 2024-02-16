package com.museumPlus.dataImporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.museumPlus.entity.User;

public class CSVDataImporter {

	public final File file;

	public CSVDataImporter(File file) {
		this.file = file;
	}

	public List<User> importData() throws FileNotFoundException, ParseException {

		Scanner scanner = new Scanner(file);
		List<User> users = new ArrayList<>();
		scanner.nextLine();

		while (scanner.hasNextLine()) {

			String[] userAsString = scanner.nextLine().split(",");

			User user = new User(userAsString[1], userAsString[2]);

			user.setLogin(userAsString[0]);
			user.setDepartment(userAsString[3]);
			user.setDateHired(new SimpleDateFormat("yyyy-MM-dd").parse(userAsString[4]));
			user.setBio(userAsString[5]);

			users.add(user);
		}

		scanner.close();
		return users;
	}
}
