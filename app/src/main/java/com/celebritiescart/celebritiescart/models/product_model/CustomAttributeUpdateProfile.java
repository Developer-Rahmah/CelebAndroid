package com.celebritiescart.celebritiescart.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomAttributeUpdateProfile {

    public CustomAttributeUpdateProfile() {

    }
    public CustomAttributeUpdateProfile(String attributeCode, UpdateValue value) {
        super();
        this.attributeCode = attributeCode;
        this.value = value;
    }
    @SerializedName("attribute_code")
    @Expose
    private String attributeCode;
    @SerializedName("value")
    @Expose
    private UpdateValue value;
    public String getAttributeCode() {
        return attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    public UpdateValue getValue() {
        return value;
    }

    public void setValue(UpdateValue value) {
        this.value = value;
    }
}

