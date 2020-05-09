package application;

import javafx.scene.control.Hyperlink;

public class Disease {
	public String name;
	
	private String fxml;
	
	
	
	public Disease(String name, String fxml) {
		super();
		this.name = name;
		this.fxml = fxml;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFxml() {
		return fxml;
	}

	public void setFxml(String fxml) {
		this.fxml = fxml;
	}

	public Hyperlink getLink() {
		Hyperlink link = new Hyperlink();
		link.setText(name);
		
		return link;
	}
	
}
