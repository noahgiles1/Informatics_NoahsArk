package application;

import java.awt.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import javafx.scene.shape.Path;

public class toMarkUp {
	public static DataObject liveData;
	static Country chosenCountry;

	public static void RDFa(String countryString) throws IOException {
		String selectedCountry = countryString;
		
		//Gets list of countries and find the country chosen by the user.
		liveData = DataAPIs.liveDataAPI();
		for (Country country : liveData.getCountries()) {
			if (selectedCountry.equals(country.getCountry())) {
				chosenCountry = country;
			}
		}

		//Writes the country data in RDFa format according to SpecialAnnouncement Schema
		try (FileWriter file = new FileWriter(chosenCountry.getCountry() + "_RDFA.rdf")) {
			file.write("<div vocab=\"http://schema.org/\" typeof=\"SpecialAnnouncement\">\n");
			file.write("<div property=\"datePosted\">" + chosenCountry.getDate() + "\n");
			file.write("<div property=\"spatialCoverage\" typeof=\"place\">\n");
			file.write("<span property=\"type\">Country</span>\n");
			file.write("<span property=\"name\">" + chosenCountry.getCountry() + "</span>\n");
			file.write("</div>\n");
			file.write("<div property=\"diseaseSpreadStatistics\" typeof=\"observation\">\n");
			file.write("<span property=\"TotalRecovered\">" + chosenCountry.getTotalRecovered()  + "</span>\n");
			file.write("<span property=\"TotalConfirmed\">" + chosenCountry.getTotalConfirmed()  + "</span>\n");
			file.write("<span property=\"TotalDeaths\">" + chosenCountry.getTotalDeaths()  + "</span>\n");
			file.write("</div>\n");
			file.write("</div>\n");

			java.nio.file.Path p = Paths.get(chosenCountry.getCountry() + "_RDFA.rdf");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Data successfully saved to RDFa format. \n Location: " + p.toAbsolutePath());

	 } catch (IOException e) {
         e.printStackTrace();
     }



	}

	public static void jsonLd(String countryString) throws IOException {
		String selectedCountry = countryString;
		
		//Gets list of countries and find the country chosen by the user.
		liveData = DataAPIs.liveDataAPI();
		for (Country country : liveData.getCountries()) {
			if (selectedCountry.equals(country.getCountry())) {
				chosenCountry = country;
			}
		}

		//Creating json objects using gson to be put into JSON-LD format
		JSONLD json0 = new JSONLD();
		json0.setName(chosenCountry.getCountry());
		json0.setType("Country");
		JSONLD json1 = new JSONLD();
		json1.setTotalConfirmed(Integer.toString(chosenCountry.getTotalConfirmed()));
		json1.setTotalRecovered(Integer.toString(chosenCountry.getTotalRecovered()));
		json1.setTotalDeaths(Integer.toString(chosenCountry.getTotalDeaths()));
		JSONLD json = new JSONLD();
		json.setSpatialCoverage(json0);
		json.setContext("https://schema.org");
		json.setDatePosted(chosenCountry.getDate());
		json.setDiseaseSpreadStatistics(json1);
		json.setType("SpecialAnnouncement");
		Gson jsonld = new Gson();
		String response = jsonld.toJson(json);

		//Writing the country to JSON-LD format using SpecialAnnouncement schema
		try (FileWriter file = new FileWriter(chosenCountry.getCountry() + "_JSON-LD.jsonld")) {
			file.write("<script type=\"application/ld+json\">\n");
            file.write(response.toString());
            file.write("\n</script>");
            file.flush();
            
            java.nio.file.Path p = Paths.get(chosenCountry.getCountry() + "_JSON-LD.jsonld");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Data successfully saved to JSON-LD format. \n Location: " + p.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
	}


}
