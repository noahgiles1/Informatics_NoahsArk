package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class FourthController {
	
	@FXML LineChart<String,Number> lineChart;
	@SuppressWarnings("unchecked")
	public void btn(ActionEvent event) {
		lineChart.getData().clear();
		XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();               
		series.getData().add(new XYChart.Data<String, Number> ("Jan",200));
		series.getData().add(new XYChart.Data<String, Number> ("Feb",300));
		series.getData().add(new XYChart.Data<String, Number> ("Mar",100));
		series.getData().add(new XYChart.Data<String, Number> ("Apr",500));
		series.getData().add(new XYChart.Data<String, Number> ("May",600));
		series.setName("Month");
		lineChart.setCreateSymbols(false);
		lineChart.setCursor(Cursor.CROSSHAIR);
		
		
		XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();               
		series1.getData().add(new XYChart.Data<String, Number> ("Jan",400));
		series1.getData().add(new XYChart.Data<String, Number> ("Feb",100));
		series1.getData().add(new XYChart.Data<String, Number> ("Mar",500));
		series1.getData().add(new XYChart.Data<String, Number> ("Apr",800));
		series1.getData().add(new XYChart.Data<String, Number> ("May",600));
		series1.setName("Number");
		lineChart.setCreateSymbols(false);
		lineChart.getData().addAll(series, series1);
	
	}

}
