package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static DataObject liveData;
	public static CountryAge[] popAge;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		liveData = DataAPIs.liveDataAPI();
		popAge = DataAPIs.populationAgeAPI();
		
		for(Country country : liveData.getCountries()) {
			country.setCountryAge(popAge); // links the data from the popluation age API with the data from the covid API
		}
		
		launch(args);
	}
}
