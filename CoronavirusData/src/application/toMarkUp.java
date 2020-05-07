package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.schemaorg.JsonLdSerializer;
import com.google.schemaorg.JsonLdSyntaxException;
import com.google.schemaorg.core.CoreFactory;
import com.google.schemaorg.core.Thing;
import com.google.schemaorg.core.datatype.Text;

public class toMarkUp {
	public static DataObject liveData;
	static Country chosenCountry;
	
	public static void RDFa(String countryString) throws IOException {
		String hello = countryString;
		liveData = DataAPIs.liveDataAPI();
		for (Country country : liveData.getCountries()) {
			if (hello.equals(country.getCountry())) {
				chosenCountry = country;
			}
		}
		
		try (FileWriter file = new FileWriter(chosenCountry.getCountry() + "_RDFA.html")) {
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
			file.flush();
		
	 } catch (IOException e) {
         e.printStackTrace();
     }
		
		
		
	}
	
	public static void jsonLd(String countryString) throws IOException {
		String hello = countryString;
		liveData = DataAPIs.liveDataAPI();
		for (Country country : liveData.getCountries()) {
			if (hello.equals(country.getCountry())) {
				chosenCountry = country;
			}
		}
		
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
		
		try (FileWriter file = new FileWriter(chosenCountry.getCountry() + "_JSON-LD.json")) {
			file.write("<script type=\"application/ld+json\">\n"); 
            file.write(response.toString());
            file.write("\n</script>"); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

}
