package application;
public class DataObject
{
    private Country[] Countries;

    private Global Global;

    private String Date;

    public Country[] getCountries ()
    {
        return Countries;
    }

    public void setCountries (Country[] Countries)
    {
        this.Countries = Countries;
    }

    public Global getGlobal ()
    {
        return Global;
    }

    public void setGlobal (Global Global)
    {
        this.Global = Global;
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
        return "[Countries = "+Countries+", Global = "+Global+", Date = "+Date+"]";
    }
}