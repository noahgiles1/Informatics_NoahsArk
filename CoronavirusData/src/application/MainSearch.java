package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainSearch extends Application{
	@Override
	public void start(Stage primaryStage) { // loads the main fxml page
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("MainSearch.fxml")); 
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {

		launch(args);
		
	}
}
