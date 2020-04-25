package Test_Graph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class API {
	public static void main(String[] args) {
		
		
		try {
			
		
		String url = "https://api.covid19api.com/total/dayone/country/united-kingdom";
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
	
	
		for(ArrayList<String> str : getData(response)) {
			System.out.println("deaths: " + str.get(1));
			System.out.println("date: " + str.get(3));
			System.out.println();
		}
		
		getData(response);
		
		
		}
		
		catch (Exception e){
			System.out.println(e);
		}
			
		
	}
	
	public static ArrayList<ArrayList<String>> getData(StringBuffer response) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.create();
		dayOneAPI[] dayOne = gson.fromJson(response.toString(), dayOneAPI[].class);
		
		ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>();
		for (int i=0; i < dayOne.length; i++) {
			String csv = dayOne[i].toString();
			String[] elements = csv.split(", ");
			ArrayList<String> fixedLengthList = new ArrayList<>(Arrays.asList(elements));
			fixedLengthList.remove(0);
			fixedLengthList.remove(1);
			fixedLengthList.remove(3);
			fixedLengthList.remove(3);
			fixedLengthList.remove(4);
			fixedLengthList.remove(4);
			fixedLengthList.remove(4);
			fixedLengthList.remove(1);
			fixedLengthList.set(0, fixedLengthList.get(0).replaceAll("[^\\d.]", ""));
			fixedLengthList.set(1, fixedLengthList.get(1).replaceAll("[^\\d.]", ""));
			fixedLengthList.set(2, fixedLengthList.get(2).replaceAll("[^\\d.]", ""));
			fixedLengthList.set(3, fixedLengthList.get(3).substring(7, 17));
			str.add(fixedLengthList);
		}
		
		//IN ORDER - RECOVERED, DEATHS, CONFIRMED, DATE
		return str;
				
	}

}
