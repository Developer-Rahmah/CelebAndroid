package com.celebritiescart.celebritiescart.models.city_model;

public class Countries
{
    private String code;

    private String lable;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getLable ()
    {
        return lable;
    }

    public void setLable (String lable)
    {
        this.lable = lable;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", lable = "+lable+"]";
    }
}