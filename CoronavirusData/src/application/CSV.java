package application;

import java.io.FileWriter;
import java.io.IOException;

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
	}
}
