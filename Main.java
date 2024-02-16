package com.museumPlus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.museumPlus.dataImporter.CSVDataImporter;
import com.museumPlus.entity.User;

@SpringBootApplication
public class Main {

	public static void main(String[] args)
			throws IOException, ParseException, InterruptedException, URISyntaxException {

		SpringApplication.run(Main.class, args);

		File file = new File("C:\\Users\\ACER\\eclipse-workspace\\javaApp_museumPlus\\src\\main\\resources\\data.csv");
		CSVDataImporter cvsDataImporter = new CSVDataImporter(file);
		List<User> users = cvsDataImporter.importData();

		addAllUsersAndDisplayTheirDetails(users);
	}

	private static Optional<User> addUserByCallingPostRequestAPI(String userAsJSONString)
			throws IOException, URISyntaxException {

		String address = "http://localhost:8080/reqres.in/users";
		URL url = new URI(address).toURL();
		HttpURLConnection connection = (HttpURLConnection) (url).openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");

		connection.setDoOutput(true);
		DataOutputStream write = new DataOutputStream(connection.getOutputStream());
		write.writeBytes(userAsJSONString);
		write.flush();
		write.close();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();

		while ((output = bufferedReader.readLine()) != null) {
			response.append(output);
		}
		bufferedReader.close();

		if (response.isEmpty()) {
			return java.util.Optional.empty();
		}

		Optional<User> user = Optional.of(new ObjectMapper().readValue(response.toString(), User.class));
		return user;
	}

	private static void addAllUsersAndDisplayTheirDetails(List<User> users) throws IOException, URISyntaxException {
		int countAddedUsers = 0;

		for (User user : users) {

			String userAsJSONString = new ObjectMapper().writeValueAsString(user);
			Optional<User> addedUser = addUserByCallingPostRequestAPI(userAsJSONString);

			if (addedUser.isPresent()) {
				System.out.println("Created user " + user.getLogin() + ", assigned id = " + addedUser.get().getId());
				++countAddedUsers;
			}
		}
		System.out.println("Imported " + countAddedUsers + " users");
	}
}
