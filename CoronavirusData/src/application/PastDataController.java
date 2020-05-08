package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
		Parent root = FXMLLoader.load(getClass().getResource("COVID-19.fxml")); // loads main page

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}


	public void btn1(ActionEvent event) throws IOException {

		if (selectC.getValue() == null) {
			// error popup if a country is not selected
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Country Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a country from the dropdown box.");
			alert.showAndWait();
			return;
		}
		if (selectg.getValue() == null) {
			// error popup if a type is not selected
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Graph Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select the type of graph you wish to view from the dropdown box.");
			alert.showAndWait();
			return;
		}

		// finds the corresponding country object in liveData
		for (Country country : COVID19Controller.liveData.getCountries()) {
			if (selectC.getValue().equals(country.getCountry())) {
				lineChart.setTitle(selectC.getValue() + " Stats");
				chosenCountry = country;
			}
		}


		DayOne[] countryData = DataAPIs.dayOneAPI(chosenCountry); // uses the dayone api to get the past data for the country

		CSV.writeCSV(countryData, chosenCountry.getCountry()); //Writes countrys past data to csv
	}



	@SuppressWarnings("unchecked")
	public void btn(ActionEvent event) throws InterruptedException, IOException {
		if (selectC.getValue() == null) {
			// error popup if a country is not selected
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Country Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a country from the dropdown box.");
			alert.showAndWait();
			return;
		}
		if (selectg.getValue() == null) {
			// error popup if type is not selected
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Graph Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select the type of graph you wish to view from the dropdown box.");
			alert.showAndWait();
			return;
		}

		lineChart.getData().clear(); // resets the graph

		XYChart.Series<String,Number> deaths= new XYChart.Series<String,Number>();
		XYChart.Series<String,Number> cases= new XYChart.Series<String,Number>();
		XYChart.Series<String,Number> recovered= new XYChart.Series<String,Number>();

		for (Country country : COVID19Controller.liveData.getCountries()) {
			// finds the corresponding country object in liveData
			if (selectC.getValue().equals(country.getCountry())) {
				lineChart.setTitle(selectC.getValue() + " Stats");
				chosenCountry = country;
			}
		}

		DayOne[] countryData = DataAPIs.dayOneAPI(chosenCountry); // uses the dayone api to get the past data for the country


		for(DayOne dataPoint : countryData) {
			if (Integer.parseInt(dataPoint.getConfirmed()) > 99 ) { 
				// only adds the data from the point the country had over 100 cases
				deaths.getData().add(new XYChart.Data<String, Number> (dataPoint.getDate().substring(0, 10),Integer.parseInt(dataPoint.getDeaths())));
				cases.getData().add(new XYChart.Data<String, Number> (dataPoint.getDate().substring(0, 10),Integer.parseInt(dataPoint.getConfirmed())));
				recovered.getData().add(new XYChart.Data<String, Number> (dataPoint.getDate().substring(0, 10),Integer.parseInt(dataPoint.getRecovered())));
			}
		}

		lineChart.setCreateSymbols(false);
		lineChart.setAnimated(false);
		
		cases.setName("Confirmed Cases");
		deaths.setName("Deaths");
		recovered.setName("Recovered");

		if (selectg.getValue().equals("Deaths")){
			y_axis.setText("No. of Deaths");
			y_axis.setRotate(270.0);
			lineChart.getData().addAll(deaths);

		}
		else if (selectg.getValue().equals("Confirmed Cases")){
			y_axis.setText("No. of Confirmed Cases");
			y_axis.setRotate(270.0);
			lineChart.getData().addAll(cases);
		}
		else if (selectg.getValue().equals("Recovered")){
			y_axis.setText("No. of Recoveries");
			y_axis.setRotate(270.0);
			lineChart.getData().addAll(recovered);
		}
		else if (selectg.getValue().equals("All")){
			y_axis.setText("");
			y_axis.setRotate(270.0);
			lineChart.getData().addAll(cases, deaths, recovered);
		}

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// on fxml load
		selectg.setItems(list); // adds the OrganisableList to the selectg items
		
		ObservableList<String>  data = FXCollections.observableArrayList();
		
		for(Country country : COVID19Controller.liveData.getCountries()) {
			// only adds countries with over 100 cases
			if ((country.getTotalConfirmed()) > 99) {
				data.add(country.getCountry());
			}
		}
		selectC.setItems(data);

	}
}
