package application;

import java.awt.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class CSV {
	public static void writeCSV(DayOne[] countryData, String country) throws IOException {
		FileWriter csvWriter = new FileWriter(country + "_Data.csv");
		csvWriter.append(String.join(",", "Date","Confirmed Cases","Deaths", "Recovered", country));
		csvWriter.append("\n");
		
		for(DayOne dataPoint : countryData) {
			csvWriter.append(String.join(",", dataPoint.getDate().substring(0, 10).toString(), dataPoint.getConfirmed(), dataPoint.getDeaths(), dataPoint.getRecovered()));
			csvWriter.append("\n");
		}
		

		csvWriter.flush();
		csvWriter.close();
		
		java.nio.file.Path p = Paths.get(country + "_Data.csv");
		Component frame = null;
		JOptionPane.showMessageDialog(frame, "Data successfully saved to CSV format. \n Location: " + p.toAbsolutePath());
	}
}
