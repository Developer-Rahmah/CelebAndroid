package com.celebritiescart.celebritiescart.models.product_model;

public class UpdateValue
{
    private String base64EncodedData;

    private String name;

    private String type;

    public String getBase64EncodedData ()
    {
        return base64EncodedData;
    }

    public void setBase64EncodedData (String base64EncodedData)
    {
        this.base64EncodedData = base64EncodedData;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }


}