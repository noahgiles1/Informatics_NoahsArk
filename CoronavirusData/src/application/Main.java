package application;

import java.awt.Component;
import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
	@Override
	public void start(Stage primaryStage) { // loads the main fxml page
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("Main.fxml")); 
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			
			primaryStage.setTitle("Bio-Statlab");
            Image image = new Image(getClass().getResourceAsStream("/Icons/Untitled.png"));
            primaryStage.getIcons().add(image);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Process process = java.lang.Runtime.getRuntime().exec("ping www.google.co.uk"); // tests internet connection
		int x = process.waitFor();
		if (x != 0) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "There seems to be a problem with your internet connection, please try again.");
		}
		launch(args);
	}
}
