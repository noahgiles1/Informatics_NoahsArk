package application;

import java.awt.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class COVID19Controller implements Initializable{
	
    @FXML
    private Button LiveData;

    @FXML
    private Button PastData;

    @FXML
    private Button CompareCountries;
    
    @FXML
    private Button DeathsAge;
    
    @FXML
    private Button homeBtn;
    
    public static DataObject liveData;
    public static CountryAge[] popAge;

    @FXML
    void homeEvent(ActionEvent event) throws IOException {
    	Stage stage = (Stage) homeBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml")); // loads main page

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void HandleButtonEvent(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        // chooses between the pages available depending on the event source
        if(event.getSource()==LiveData){
            stage = (Stage) LiveData.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("LiveDataScreen.fxml"));
        }
        else if(event.getSource()==PastData) {
            stage = (Stage) PastData.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("PastData.fxml"));
        }
        else if(event.getSource()==CompareCountries) {
            stage = (Stage) CompareCountries.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("CompareCountries.fxml"));
        }
        else if(event.getSource()==DeathsAge) {
            stage = (Stage) DeathsAge.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AgeScatter.fxml"));
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	try {
    		liveData = DataAPIs.liveDataAPI(); // current data api

    		popAge = DataAPIs.populationAgeAPI(); // population age api

    		for(Country country : liveData.getCountries()) {
    			country.setCountryAge(popAge); // links the data from the population age API with the data from the covid API
    		}
    	}
    	catch(IOException a) {
    	} 
    }

}
