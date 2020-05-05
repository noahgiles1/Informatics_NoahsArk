package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class DataAPIs {
	public static DataObject liveDataAPI() throws IOException {

		String url = "https://api.covid19api.com/summary";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		Gson gson = new Gson();
		DataObject liveData = gson.fromJson(response.toString(), DataObject.class);

		return liveData;

	}

	public static DayOne[] dayOneAPI(Country country) throws IOException {
		
		StringBuffer response = new StringBuffer();

		String url = String.format("https://api.covid19api.com/total/dayone/country/%s",  country.getCountry());
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();
		System.out.println("test"+response);
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		DayOne[] dayOne = gson.fromJson(response.toString(), DayOne[].class);

		return dayOne;
	}
	
	public static CountryAge[] populationAgeAPI() throws IOException {
		StringBuffer response = new StringBuffer();

		String url = "http://api.worldbank.org/v2/country/all/indicator/SP.POP.65UP.TO.ZS?date=2018&per_page=1000&format=json";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;

		
		while ((inputLine = in.readLine()) != null) {
			
			String inputLine2 = inputLine.replaceFirst("\\{.*?\\}", "");
			System.out.println(inputLine2);
			String inputLine3 = inputLine2.substring(2, inputLine2.length()-1);
			System.out.println(inputLine3);
			response.append(inputLine3);
		}
		System.out.println("test"+response);
		

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		CountryAge[] countriesAges = gson.fromJson(response.toString(), CountryAge[].class);
		System.out.println(countriesAges.toString());
		return countriesAges;
	}
}


