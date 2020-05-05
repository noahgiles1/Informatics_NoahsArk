package application;




import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class PastDataController implements Initializable {
    @FXML Button homeBtn;
	@FXML LineChart<String,Number> lineChart;
	@FXML Label lbl;
	@FXML public ComboBox<String> selectg;
	@FXML public ComboBox<String> selectC;
	@FXML public Label y_axis;
	@FXML public NumberAxis xAxis;
	
	
	ObservableList<String> list = FXCollections.observableArrayList("Confirmed Cases", "Deaths", "Recovered", "All");
	Country chosenCountry;
	
	@FXML
	public void homeEvent(ActionEvent event) throws IOException {

		Stage stage = (Stage) homeBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@SuppressWarnings("unchecked")
	public void btn(ActionEvent event) throws InterruptedException, IOException {
		System.out.println(selectC.getValue());
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


		lineChart.getData().clear();

		XYChart.Series<String,Number> deaths= new XYChart.Series<String,Number>(); 
		XYChart.Series<String,Number> cases= new XYChart.Series<String,Number>(); 
		XYChart.Series<String,Number> recovered= new XYChart.Series<String,Number>();


		for (Country country : Main.liveData.getCountries()) {
			if (selectC.getValue().equals(country.getCountry())) {
				lineChart.setTitle(selectC.getValue() + " Stats");
				chosenCountry = country;
			}
		}

		DayOne[] countryData = DataAPIs.dayOneAPI(chosenCountry);

		for(DayOne dataPoint : countryData) {
			deaths.getData().add(new XYChart.Data<String, Number> (dataPoint.getDate().substring(0, 10),Integer.parseInt(dataPoint.getDeaths())));
			cases.getData().add(new XYChart.Data<String, Number> (dataPoint.getDate().substring(0, 10),Integer.parseInt(dataPoint.getConfirmed())));
			recovered.getData().add(new XYChart.Data<String, Number> (dataPoint.getDate().substring(0, 10),Integer.parseInt(dataPoint.getRecovered())));
		}
		/* for(ArrayList<String> str : getData(response)) {
				 int i = Integer.parseInt(str.get(1));
				 	cases.getData().add(new XYChart.Data<String, Number> (str.get(3),i));
				}
			 for(ArrayList<String> str : getData(response)) {
				 int i = Integer.parseInt(str.get(0));
				 	recovered.getData().add(new XYChart.Data<String, Number> (str.get(3),i));
				}
		 */
		lineChart.setCreateSymbols(false);
		lineChart.setAnimated(false);
		cases.setName("Confirmed Cases");
		deaths.setName("Deaths");
		recovered.setName("Recovered");

		if (selectg.getValue().equals("Deaths")){
			lineChart.getData().addAll(deaths);
			
		}
		else if (selectg.getValue().equals("Confirmed Cases")){
			lineChart.getData().addAll(cases);
		}
		else if (selectg.getValue().equals("Recovered")){
			lineChart.getData().addAll(recovered);
		}
		else if (selectg.getValue().equals("All")){
			lineChart.getData().addAll(cases, deaths, recovered);
		}
		



		/*for (final XYChart.Data<String, Number> data : series.getData()) {
				Tooltip.install(data.getNode(), new Tooltip("X :" + data.getXValue() + "\n Y :" + String.valueOf(data.getYValue())));	
					}

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
		 */
	}







	public static String[] getData1(StringBuffer response) {
		Gson gson = new Gson();
		DataObject myPojo = gson.fromJson(response.toString(), DataObject.class);

		for(int i=0; i < myPojo.getCountries().length; i++) {
			System.out.println(myPojo.getCountries()[i]);
		}

		return new String[] {
				"Global Stats: " + myPojo.getGlobal()
		};

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
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		selectg.setItems(list);
		ObservableList<String>  data = FXCollections.observableArrayList();
		for(Country country : Main.liveData.getCountries()) {
			data.add(country.getCountry());
		}
		selectC.setItems(data);
		xAxis.setLowerBound(25000);
	}

}






