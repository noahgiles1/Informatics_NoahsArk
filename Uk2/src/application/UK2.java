package application;
	
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;


public class UK2 extends Application {
	public static ArrayList<ArrayList<String>> Countries = new ArrayList<ArrayList<String>>();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("UK2.fxml"));
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
			try {

			String url = "https://api.covid19api.com/summary";
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
				return;
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
		
			getData1(response);
			}
			
			catch (Exception e){
				System.out.println(e);
			}
		launch(args);
	}
	
	public static void getData1(StringBuffer response) {
		Gson gson = new Gson();
		MyPojo myPojo = gson.fromJson(response.toString(), MyPojo.class);
		
		for(int i=0; i < myPojo.getCountries().length; i++) {
			String csv = myPojo.getCountries()[i].toString();
			String[] elements = csv.split(", ");
			ArrayList<String> fixedLengthList = new ArrayList<>(Arrays.asList(elements));
			ArrayList <String> List = new ArrayList<>();
			List.add(fixedLengthList.get(4));
			List.add(fixedLengthList.get(6));
			List.set(0, List.get(0).substring(10));
			List.set(1, List.get(1).substring(7));
			if (Integer.parseInt(fixedLengthList.get(3).substring(17)) > 99) {
				Countries.add(List);
			}
		
		}
		
	}
	
	public static void countryList() {
		
	}
}
