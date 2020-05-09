package application;

public class JSONLD {
	private String context;
	
	private String type;
	
	private String TotalRecovered;
	
	private String TotalConfirmed;
	
	private String TotalDeaths;
	
	private String datePosted;
	
	private String name;
	
	
	private JSONLD spatialCoverage;
	
	private JSONLD diseaseSpreadStatistics;
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	public String getTotalRecovered ()
	   {
	       return TotalRecovered;
	   }

	public void setTotalRecovered (String i)
	   {
	       this.TotalRecovered = i;
	   }

	public String getTotalConfirmed() {
		return TotalConfirmed;
	}

	public void setTotalConfirmed(String i) {
		this.TotalConfirmed = i;
	}

	public String getTotalDeaths() {
		return TotalDeaths;
	}

	public void setTotalDeaths(String i) {
		this.TotalDeaths = i;
	}

	public String getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public JSONLD getSpatialCoverage() {
		return spatialCoverage;
	}
	
	public void setSpatialCoverage(JSONLD type) {
		this.spatialCoverage = type;
	}

	public JSONLD getDiseaseSpreadStatistics() {
		return diseaseSpreadStatistics;
	}

	public void setDiseaseSpreadStatistics(JSONLD diseaseSpreadStatistics) {
		this.diseaseSpreadStatistics = diseaseSpreadStatistics;
	}
	
	
}
