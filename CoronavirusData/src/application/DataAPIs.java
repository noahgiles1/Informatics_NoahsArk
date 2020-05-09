package application;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class DataAPIs {
	public static void checkAPI(int responseCode) {
		Component frame = null;
		if (responseCode != 200) {
			JOptionPane.showMessageDialog(frame, "There was a problem retrieving data from the API. \n We will try to use a local backup \n" + "Error code: " + responseCode);
		}
	}


	public static DataObject liveDataAPI() throws IOException {
		//API call for live country data
		String url = "https://api.covid19api.com/summary";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		int responseCode;
		Object inputLine;
		StringBuffer response = new StringBuffer();
		try {
			responseCode = con.getResponseCode();
			System.out.println("Sending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		}
		catch (UnknownHostException e) {
			responseCode = 0;
		}

		checkAPI(responseCode);

		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			FileWriter file = new FileWriter("liveDataBackUp.json"); // write API data to backup file
			file.write(response.toString());
			file.flush();
			file.close();
			in.close();
		}
		else if (responseCode != 200) {
			try {
				File myObj = new File("liveDataBackUp.json");  // check for backup data if no data returned from API
				Scanner myReader = new Scanner(myObj);
				String data = myReader.nextLine();
				response.append(data);
				myReader.close();
			}
			catch (FileNotFoundException e) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "No backup file found");
				return null;
			}

		}

		//Turning the data from API call into an object and returning it
		Gson gson = new Gson();
		DataObject liveData = gson.fromJson(response.toString(), DataObject.class);

		return liveData;

	}

	public static DayOne[] dayOneAPI(Country country) throws IOException {
		//API call for a countrys data since their first case
		StringBuffer response = new StringBuffer();

		String url = String.format("https://api.covid19api.com/total/dayone/country/%s",  country.getCountry());
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		int responseCode;
		try {
			responseCode = con.getResponseCode();
			System.out.println("Sending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		}
		catch (UnknownHostException e) {
			responseCode = 0;
		}



		checkAPI(responseCode);

		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			FileWriter file = new FileWriter(country.getCountry() + "pastDataBackUp.json");
			file.write(response.toString());
			file.flush();
			file.close();
			in.close();
		}
		else if (responseCode != 200) {
			try {
				File myObj = new File(country.getCountry() + "pastDataBackUp.json"); // check for backup data if no data returned from API
				Scanner myReader = new Scanner(myObj);
				String data = myReader.nextLine();
				response.append(data);
				myReader.close();
			}
			catch (FileNotFoundException e) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "No backup file found");
				//return null;
			}
		}

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		//Turning the data from API call into an object and returning it
		DayOne[] dayOne = gson.fromJson(response.toString(), DayOne[].class);

		return dayOne;
	}

	public static CountryAge[] populationAgeAPI() throws IOException {

		//API call for countries population data
		StringBuffer response = new StringBuffer();

		String url = "http://api.worldbank.org/v2/country/all/indicator/SP.POP.65UP.TO.ZS?date=2018&per_page=1000&format=json"; // population age API 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		int responseCode;
		try {
			responseCode = con.getResponseCode();
			System.out.println("Sending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		}
		catch (UnknownHostException e) {
			responseCode = 0;
		}

		checkAPI(responseCode);

		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); // if API call is successful
			String inputLine;


			while ((inputLine = in.readLine()) != null) {
				// removes the header of the json file which contains data we do not need
				String inputLine2 = inputLine.replaceFirst("\\{.*?\\}", "");
				String inputLine3 = inputLine2.substring(2, inputLine2.length()-1);

				response.append(inputLine3);
			}
			FileWriter file = new FileWriter("populationBackUp.json"); // writes the api data to a backup file
			file.write(response.toString());
			file.flush();
			file.close();
			in.close();
		}
		else if (responseCode != 200) {
			try {
				File myObj = new File("populationBackUp.json"); // if the api does not return the data, check for backup data first, and load if there
				Scanner myReader = new Scanner(myObj);
				String data = myReader.nextLine();
				response.append(data);
				myReader.close();
			}
			catch (FileNotFoundException e) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "No backup file found");
				return null;
			}
		}


		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		//Turning the data from API call into an object and returning it
		CountryAge[] countriesAges = gson.fromJson(response.toString(), CountryAge[].class);
		return countriesAges;
	}
}
