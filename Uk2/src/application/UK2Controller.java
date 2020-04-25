package application;




import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


public class UK2Controller {

	@FXML LineChart<String,Number> lineChart;
	@FXML Label lbl;
	
	public void btn(ActionEvent event) {
		lineChart.getData().clear();

//			
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
		

		      XYChart.Series<String,Number> series= new XYChart.Series<String,Number>(); 
		    
		     
				
			
			 
			
			 for(ArrayList<String> str : getData(response)) {
				 int i = Integer.parseInt(str.get(2));
				 	series.getData().add(new XYChart.Data<String, Number> (str.get(3),i));
				}
			 lineChart.setCreateSymbols(false);
			 lineChart.getData().add(series);

				for (final XYChart.Data<String, Number> data : series.getData()) {
					Tooltip.install(data.getNode(), new Tooltip("X :" + data.getXValue() + "\n Y :" + String.valueOf(data.getYValue())));
				
				
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

	
		
	
	

