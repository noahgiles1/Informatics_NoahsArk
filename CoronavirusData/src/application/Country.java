package application;

import java.util.Locale;
import java.util.MissingResourceException;

public class Country
{
    private String NewRecovered;

    private String NewDeaths;

    private String TotalRecovered;

    private String TotalConfirmed;

    private String Country;

    private String CountryCode;

    private String Slug;

    private String NewConfirmed;

    private String TotalDeaths;

    private String Date;
    
    private CountryAge countryAge;

    public String getNewRecovered ()
    {
        return NewRecovered;
    }

    public void setNewRecovered (String NewRecovered)
    {
        this.NewRecovered = NewRecovered;
    }

    public String getNewDeaths ()
    {
        return NewDeaths;
    }

    public void setNewDeaths (String NewDeaths)
    {
        this.NewDeaths = NewDeaths;
    }

    public int getTotalRecovered ()
    {
        return Integer.parseInt(TotalRecovered);
    }

    public void setTotalRecovered (String TotalRecovered)
    {
        this.TotalRecovered = TotalRecovered;
    }

    public int getTotalConfirmed ()
    {
    	return Integer.parseInt(TotalConfirmed);
    }

    public void setTotalConfirmed (String TotalConfirmed)
    {
        this.TotalConfirmed = TotalConfirmed;
    }

    public String getCountry ()
    {
        return Country;
    }

    public void setCountry (String Country)
    {
        this.Country = Country;
    }

    public String getCountryCode ()
    {
        return CountryCode;
    }

    public void setCountryCode (String CountryCode)
    {
        this.CountryCode = CountryCode;
    }

    public String getSlug ()
    {
        return Slug;
    }

    public void setSlug (String Slug)
    {
        this.Slug = Slug;
    }

    public String getNewConfirmed ()
    {
        return NewConfirmed;
    }

    public void setNewConfirmed (String NewConfirmed)
    {
        this.NewConfirmed = NewConfirmed;
    }

    public int getTotalDeaths ()
    {
    	return Integer.parseInt(TotalDeaths);
    }

    public void setTotalDeaths (String TotalDeaths)
    {
        this.TotalDeaths = TotalDeaths;
    }

    public String getDate ()
    {
        return Date;
    }

    public void setDate (String Date)
    {
        this.Date = Date;
    }
    
    public double getDeathRate() {
    	Double deathRate= Double.parseDouble(TotalDeaths)/Double.parseDouble(TotalConfirmed)*100;
    	
    	return Math.round(deathRate);
    }

    public void setCountryAge(CountryAge[] countryAges) {
    	for(CountryAge country: countryAges) {
    		try {
    			Locale locale = new Locale("en",getCountryCode()); // converts the 2 digit iso country code to the 3 digit iso code

    			if (locale.getISO3Country().equals(country.getCountryiso3code())){
    				this.countryAge = country;
    			}
    		}
    		catch (MissingResourceException e){
    			//do nothing
    		}
    	}
    }
    
    public CountryAge getCountryAge() {

    	return countryAge;
    }
    
    @Override
    public String toString()
    {
        return "[NewRecovered = "+NewRecovered+", NewDeaths = "+NewDeaths+", TotalRecovered = "+TotalRecovered+", TotalConfirmed = "+TotalConfirmed+", Country = "+Country+", CountryCode = "+CountryCode+", Slug = "+Slug+", NewConfirmed = "+NewConfirmed+", TotalDeaths = "+TotalDeaths+", Date = "+Date+"]";
    }
}