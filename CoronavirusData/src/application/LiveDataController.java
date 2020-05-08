package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LiveDataController implements Initializable{

	@FXML private TableView<Country> dataTable;
	
	@FXML private TableColumn<Country, String> countryColumn;

    @FXML private TableColumn<Country, Integer> casesColumn;

    @FXML private TableColumn<Country, String> deathsColumn;

    @FXML private TableColumn<Country, String> recoveredColumn;
    
    @FXML private TableColumn<Country, Double> deathRateColumn;

	@FXML private Button HomeBtn;

	@FXML private Label globalCasesLbl;

    @FXML private Label globalDeathsLbl;

    @FXML private Label globalRecoveredLbl;
    
    @FXML private Button pastDataBtn;
    
    @FXML private Button jsonLD;
    
    @FXML private Button RDFa;
    
    
	private ObservableList<Country> data;

	@FXML
	void HandleButtonEvent(ActionEvent event) throws IOException {

		if(event.getSource()==HomeBtn){
			Stage stage = (Stage) HomeBtn.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml")); // opens main screen

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	@FXML
	void RDFaEvent(ActionEvent even) throws IOException {
		if (dataTable.getSelectionModel().getSelectedItem() != null) {
	        Country selectedCountry = dataTable.getSelectionModel().getSelectedItem(); //gets selected country from the selected row
	        toMarkUp.RDFa(selectedCountry.getCountry());
			
	    }
	}
	
	@FXML
	void jsonLDEvent(ActionEvent event) throws IOException {
		if (dataTable.getSelectionModel().getSelectedItem() != null) {
	        Country selectedCountry = dataTable.getSelectionModel().getSelectedItem(); //gets selected country from the selected row
	        toMarkUp.jsonLd(selectedCountry.getCountry());
			
	    }
	}
	
	 @FXML
	    void pastDataBtnEvent(ActionEvent event) throws IOException, InterruptedException {
		 if (dataTable.getSelectionModel().getSelectedItem() != null) {
		        Country selectedCountry = dataTable.getSelectionModel().getSelectedItem(); //gets selected country from the selected row
		        
		        Stage stage = (Stage) pastDataBtn.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("PastData.fxml"));
				
				Scene scene = new Scene(loader.load());
				stage.setScene(scene);
				stage.show();
				
				PastDataController controller = (PastDataController) loader.getController(); // grabs the controller
				
				//sets up the past data page with the corresponding country 
				controller.selectC.setValue(selectedCountry.getCountry());
				controller.selectg.setValue("All");
				controller.btn(event);
				
		    }
	    }
	 
	 @FXML
	    void rowClicked(MouseEvent event) { // if row clicked
		 	// show buttons
		 	pastDataBtn.setVisible(true);
		 	jsonLD.setVisible(true);
		 	RDFa.setVisible(true);
		 	
	    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		globalCasesLbl.setText("Total Global Confirmed Cases: "+ COVID19Controller.liveData.getGlobal().getTotalConfirmed());
		globalDeathsLbl.setText("Total Global Deaths: "+ COVID19Controller.liveData.getGlobal().getTotalDeaths());
		globalRecoveredLbl.setText("Total Global Recovered: "+ COVID19Controller.liveData.getGlobal().getTotalRecovered());
	
		data = FXCollections.observableArrayList(COVID19Controller.liveData.getCountries());
			
		countryColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("Country"));
		casesColumn.setCellValueFactory(new PropertyValueFactory<Country, Integer>("TotalConfirmed"));
		deathsColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("TotalDeaths"));
		recoveredColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("TotalRecovered"));
		deathRateColumn.setCellValueFactory(new PropertyValueFactory<Country, Double>("DeathRate"));

		deathRateColumn.setCellFactory(column -> { // overrides the javafx setCellFactory
			return new TableCell<Country, Double>() {
				@Override
				protected void updateItem(Double value, boolean empty) {
					super.updateItem(value, empty);

					if (value == null || empty) { // checks for null value
						setText(null);
					} 
					
					else {

						setText(value.toString()); // sets cell text

						// sets background colour depending on death rate value
						if (value >= 15) {
							setStyle("-fx-background-color: indianred");
						}
						else if (value >= 10) {
							setStyle("-fx-background-color: orange");
						}
						else if (value >= 5) {
							setStyle("-fx-background-color: yellow");
						}
						else {
							setStyle("-fx-background-color: lightgreen");
						}
					}
				}
            };
        });

        countryColumn.setStyle( "-fx-alignment: CENTER-RIGHT;");
        casesColumn.setStyle( "-fx-alignment: CENTER-RIGHT;");
        deathsColumn.setStyle( "-fx-alignment: CENTER-RIGHT;");
        recoveredColumn.setStyle( "-fx-alignment: CENTER-RIGHT;");
        deathRateColumn.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
		dataTable.setItems(data);
		
		//sets it to sort by cases by default
		casesColumn.setSortType(TableColumn.SortType.DESCENDING);
		dataTable.getSortOrder().add(casesColumn);
		dataTable.sort();
	}
}
