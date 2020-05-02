package application;

public class DayOne {
	private String CityCode;

    private String Recovered;

    private String Active;

    private String Country;

    private String Deaths;

    private String Lon;

    private String City;

    private String Confirmed;

    private String CountryCode;

    private String Province;

    private String Lat;

    private String Date;

    public String getCityCode ()
    {
        return CityCode;
    }

    public void setCityCode (String CityCode)
    {
        this.CityCode = CityCode;
    }

    public String getRecovered ()
    {
        return Recovered;
    }

    public void setRecovered (String Recovered)
    {
        this.Recovered = Recovered;
    }

    public String getActive ()
    {
        return Active;
    }

    public void setActive (String Active)
    {
        this.Active = Active;
    }

    public String getCountry ()
    {
        return Country;
    }

    public void setCountry (String Country)
    {
        this.Country = Country;
    }

    public String getDeaths ()
    {
        return Deaths;
    }

    public void setDeaths (String Deaths)
    {
        this.Deaths = Deaths;
    }

    public String getLon ()
    {
        return Lon;
    }

    public void setLon (String Lon)
    {
        this.Lon = Lon;
    }

    public String getCity ()
    {
        return City;
    }

    public void setCity (String City)
    {
        this.City = City;
    }

    public String getConfirmed ()
    {
        return Confirmed;
    }

    public void setConfirmed (String Confirmed)
    {
        this.Confirmed = Confirmed;
    }

    public String getCountryCode ()
    {
        return CountryCode;
    }

    public void setCountryCode (String CountryCode)
    {
        this.CountryCode = CountryCode;
    }

    public String getProvince ()
    {
        return Province;
    }

    public void setProvince (String Province)
    {
        this.Province = Province;
    }

    public String getLat ()
    {
        return Lat;
    }

    public void setLat (String Lat)
    {
        this.Lat = Lat;
    }

    public String getDate ()
    {
        return Date;
    }

    public void setDate (String Date)
    {
        this.Date = Date;
    }

    @Override
    public String toString()
    {
        return "[CityCode = "+CityCode+", Recovered = "+Recovered+", Active = "+Active+", Country = "+Country+", Deaths = "+Deaths+", Lon = "+Lon+", City = "+City+", Confirmed = "+Confirmed+", CountryCode = "+CountryCode+", Province = "+Province+", Lat = "+Lat+", Date = "+Date+"]";
    }
}
