package application;

public class CountryAge {


	private String countryiso3code;

	private String date;

	private Double value;

	private String unit;

	private String obsStatus;

	private Integer decimal;

	public String getCountryiso3code() {
	return countryiso3code;
	}

	public void setCountryiso3code(String countryiso3code) {
	this.countryiso3code = countryiso3code;
	}

	public String getDate() {
	return date;
	}

	public void setDate(String date) {
	this.date = date;
	}

	public Double getValue() {
	return value;
	}

	public void setValue(Double value) {
	this.value = value;
	}

	public String getUnit() {
	return unit;
	}

	public void setUnit(String unit) {
	this.unit = unit;
	}

	public String getObsStatus() {
	return obsStatus;
	}

	public void setObsStatus(String obsStatus) {
	this.obsStatus = obsStatus;
	}

	public Integer getDecimal() {
	return decimal;
	}

	public void setDecimal(Integer decimal) {
	this.decimal = decimal;
	}
}
