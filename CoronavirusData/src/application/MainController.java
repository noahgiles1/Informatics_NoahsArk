package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController implements Initializable {
	
	@FXML
	private TextField searchField;

	@FXML
	private ListView<Disease> diseaseList;
	
	private final ObservableList<Disease> data
    = FXCollections.observableArrayList(
    		
    		// list of diseases in our system (most only lead to holding page currently)
            new Disease("SARS", "HoldingPage.fxml"),
            new Disease("Ebola", "HoldingPage.fxml"),
            new Disease("COVID-19 (coronavirus)", "COVID-19.fxml"),
            new Disease("Swine Flu", "HoldingPage.fxml"),
            new Disease("Zika", "HoldingPage.fxml"),
            new Disease("HIV/AIDS", "HoldingPage.fxml"),
            new Disease("Smallpox", "HoldingPage.fxml"),
            new Disease("Cowpox", "HoldingPage.fxml"),
            new Disease("Malaria", "HoldingPage.fxml"),
            new Disease("Cholera", "HoldingPage.fxml"),
            new Disease("Typhus", "HoldingPage.fxml"),
            new Disease("Mumps", "HoldingPage.fxml")
            
    );
	
	FilteredList<Disease> filteredData = new FilteredList<Disease>(data, p -> true); // adds the organised list to a filtered list so it can be filtered

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		searchField.setPromptText("enter filter here");
		
		diseaseList.setPlaceholder(new Label("No diseases found, please alter your search")); // placeholder text if the listview is empty
		diseaseList.setItems(filteredData); // adds the filtered list to the list items

		diseaseList.setCellFactory(new Callback<ListView<Disease>, ListCell<Disease>>() { // sets the value of the cells in the list
			@Override
			public ListCell<Disease> call(ListView<Disease> lv) {
				return new ListCell<Disease>() { // returns the following ListCell
					
					@Override
					public void updateItem(Disease value, boolean empty) {
						super.updateItem(value, empty);
						
						if (value == null) { // checks that the value is not null
							setGraphic(null);
						}
						
						else {
							// sets cell text to disease name
							Hyperlink link = value.getLink(); // grabs the link from the object

							link.setOnAction(new EventHandler<ActionEvent>() { //sets the link action
								@Override
								public void handle(ActionEvent e) {
									Stage stage = (Stage) diseaseList.getScene().getWindow();
									Parent root;
									
									try {
										root = FXMLLoader.load(getClass().getResource(value.getFxml())); // loads the fxml file from the object
										}
									
									catch (IOException e1) {
										root = null;
									} // loads main page

									Scene scene = new Scene(root);
									stage.setScene(scene);
									stage.show();
								}
							});
							
							setGraphic(link); // adds the hyperlink to the cell
						}
					}
				};
			}
		}); 

		searchField.setPromptText("Search here!");
		searchField.setOnKeyReleased(keyEvent ->
		{
			filteredData.setPredicate(p -> p.getName().toLowerCase().contains(searchField.getText().toLowerCase().trim())); // on key pressed in text field update the filter
		});
	}
}
