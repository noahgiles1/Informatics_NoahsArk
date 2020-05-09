package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AgeScatterController implements Initializable{
	@FXML
	private Button homeBtn;

	@FXML
	private ScatterChart<Number, Number> scatterGraph;

	@FXML
	void homeEvent(ActionEvent event) throws IOException {
		Stage stage = (Stage) homeBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("COVID-19.fxml")); // loads main screen

		Scene scene = new Scene(root);
		stage.setScene(scene);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		XYChart.Series<Number, Number> series= new XYChart.Series<Number, Number>(); 

		for(Country dataPoint : COVID19Controller.liveData.getCountries()) {

			if(dataPoint.getCountryAge() != null && dataPoint.getCountryAge().getValue() != null){ // checks if datapoint or the value is null
				series.getData().add(new XYChart.Data<Number, Number>(dataPoint.getCountryAge().getValue(),dataPoint.getDeathRate())); //adds the data to the graph series
			}
		}
		series.setName("Countries");
		scatterGraph.getData().add(series);
	}
}
