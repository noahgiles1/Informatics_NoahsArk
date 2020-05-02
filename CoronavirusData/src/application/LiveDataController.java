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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class LiveDataController implements Initializable{

	@FXML
	private TableView<Country> dataTable;
	
	@FXML
    private TableColumn<Country, String> countryColumn;

    @FXML
    private TableColumn<Country, Integer> casesColumn;

    @FXML
    private TableColumn<Country, String> deathsColumn;

    @FXML
    private TableColumn<Country, String> recoveredColumn;


	@FXML
	private Button HomeBtn;
	
	private ObservableList<Country> data;

	@FXML
	void HandleButtonEvent(ActionEvent event) throws IOException {

		if(event.getSource()==HomeBtn){
			Stage stage = (Stage) HomeBtn.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		data = FXCollections.observableArrayList(Main.liveData.getCountries());
		
		//dataTable.setItems(data);
		
		countryColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("Country"));
		casesColumn.setCellValueFactory(new PropertyValueFactory<Country, Integer>("TotalConfirmed"));
        deathsColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("TotalDeaths"));
        recoveredColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("TotalRecovered"));
		
		dataTable.setItems(data);
		casesColumn.setSortType(TableColumn.SortType.DESCENDING);
		dataTable.getSortOrder().add(casesColumn);
		dataTable.sort();
	}
}
