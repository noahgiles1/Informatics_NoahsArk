package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HoldingPageController {

    @FXML
    private Button backBtn;

    @FXML
    void backAction(ActionEvent event) throws IOException {
    	if(event.getSource()==backBtn){
			Stage stage = (Stage) backBtn.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml")); // opens main screen

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
    }

}
