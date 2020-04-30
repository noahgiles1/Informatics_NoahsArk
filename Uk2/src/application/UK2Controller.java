package application;




import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


public class UK2Controller implements Initializable {
	
	@FXML LineChart<String,Number> lineChart;
	@FXML Label lbl;
	@FXML public ComboBox<String> selectg;
	ObservableList<String> list = FXCollections.observableArrayList("Confirmed Cases", "Deaths", "Recovered", "All");
	@FXML public ComboBox<String> selectC;
	@FXML public Label y_axis;

	
	@SuppressWarnings("unchecked")
	public void btn(ActionEvent event) throws InterruptedException {
		lineChart.setAnimated(false);
		lineChart.getData().clear();
		if (netIsAvailable() == false) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Connection Error");
			alert.setHeaderText(null);
			alert.setContentText("Please check your internet connection and try again.");
			alert.showAndWait();
			return;
		}
		if (selectC.getValue() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Country Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a country from the dropdown box.");
			alert.showAndWait();
			return;
		}
		if (selectg.getValue() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Graph Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select the type of graph you wish to view from the dropdown box.");
			alert.showAndWait();
			return;
		}
		
			StringBuffer response = null;
			XYChart.Series<String,Number> series= new XYChart.Series<String,Number>(); 
		    XYChart.Series<String,Number> series1= new XYChart.Series<String,Number>(); 
		    XYChart.Series<String,Number> series2= new XYChart.Series<String,Number>();
			
		    
		    for (ArrayList<String> str : UK2.Countries) {
		    	if (selectC.getValue().equals(str.get(0))) {
		    		String country = str.get(1);
		    		String string = String.format("https://api.covid19api.com/total/dayone/country/%s",  country);
					System.out.println(string);
					response = APICall(string);
					lineChart.setTitle(selectC.getValue() + " Stats");
		    	}
		    }
			
			for(ArrayList<String> str : getData(response)) {
				 int i = Integer.parseInt(str.get(2));
				 	series.getData().add(new XYChart.Data<String, Number> (str.get(3),i));
				}
			 for(ArrayList<String> str : getData(response)) {
				 int i = Integer.parseInt(str.get(1));
				 	series1.getData().add(new XYChart.Data<String, Number> (str.get(3),i));
				}
			 for(ArrayList<String> str : getData(response)) {
				 int i = Integer.parseInt(str.get(0));
				 	series2.getData().add(new XYChart.Data<String, Number> (str.get(3),i));
				}
			lineChart.setCreateSymbols(false);
			
			 series.setName("Confirmed Cases");
			 series2.setName("Recovered");
			 series1.setName("Deaths");
			
						
						if (selectg.getValue().equals("Deaths")){
							y_axis.setText("No. of Deahts");
							y_axis.setRotate(270.0);
								lineChart.getData().add(series1);
						}
						else if (selectg.getValue().equals("Confirmed Cases")){
							y_axis.setText("No. of Confimred Cases");
							y_axis.setRotate(270.0);
							lineChart.getData().add(series);
						}
						else if (selectg.getValue().equals("Recovered")){
							y_axis.setText("No. of Recoveries");
							y_axis.setRotate(270.0);
							 lineChart.getData().add(series2);
						}
						
						else if (selectg.getValue().equals("All")){
							y_axis.setText("Number of ...");
							y_axis.setRotate(270.0);
							 lineChart.getData().addAll(series, series1,series2);
						}
						
			 }
	
		public static StringBuffer APICall(String country) {
			StringBuffer response = new StringBuffer();
			try {
			String url = country;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			int responseCode = con.getResponseCode();
			System.out.println("Sending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			if (responseCode != 200) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Connection Error");
				alert.setHeaderText(null);
				alert.setContentText("Unable to connect to the api. If this issue persists there may be an issue out of our control!");
				alert.showAndWait();
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			}
			catch (Exception e){
				System.out.println(e);
			}
			

			return response;
			
		}
		
		public static ArrayList<ArrayList<String>> getData(StringBuffer response) {

			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
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
				
				if (Integer.parseInt(fixedLengthList.get(2)) > 99) {
					str.add(fixedLengthList);
				}
			}
			
			
			//IN ORDER - RECOVERED, DEATHS, CONFIRMED, DATE
			return str;
		 
	}
		private static boolean netIsAvailable() {
		    try {
		        final URL url = new URL("http://www.google.com");
		        final URLConnection conn = url.openConnection();
		        conn.connect();
		        conn.getInputStream().close();
		        return true;
		    } catch (MalformedURLException e) {
		        throw new RuntimeException(e);
		    } catch (IOException e) {
		        return false;
		    }
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
			selectg.setItems(list);
			ObservableList<String>  data = FXCollections.observableArrayList();
			for(ArrayList<String> str : UK2.Countries) {
				data.add(str.get(0));
			}
			selectC.setItems(data);
			
			if (netIsAvailable() == false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Connection Error");
				alert.setHeaderText(null);
				alert.setContentText("Please check your internet connection and try again.");
				alert.showAndWait();
				return;
			}
		}		
}