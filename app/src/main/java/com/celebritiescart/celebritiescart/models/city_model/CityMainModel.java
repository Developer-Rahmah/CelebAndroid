package com.celebritiescart.celebritiescart.models.city_model;

import java.util.ArrayList;

public class CityMainModel
{
    private ArrayList<Cities> cities;

    private ArrayList<Countries> countries;

    public ArrayList<Cities> getCities ()
    {
        return cities;
    }

    public void setCities (ArrayList<Cities> cities)
    {
        this.cities = cities;
    }

    public ArrayList<Countries> getCountries ()
    {
        return countries;
    }

    public void setCountries (ArrayList<Countries> countries)
    {
        this.countries = countries;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cities = "+cities+", countries = "+countries+"]";
    }
}