
package com.celebritiescart.celebritiescart.models.product_model;

import com.celebritiescart.celebritiescart.models.user_model.EncodedImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomAttribute {

    public CustomAttribute() {

    }
    public CustomAttribute(String attributeCode, String value) {
        super();
        this.attributeCode = attributeCode;
        this.value = value;
    }
    @SerializedName("attribute_code")
    @Expose
    private String attributeCode;
    @SerializedName("value")
    @Expose
    private Object value;

    public String getAttributeCode() {
        return attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    public String getValue() {
        return value.toString();
    }

    public void setValue(String value) {
        this.value = value;
    }
    public void setValue(EncodedImage img){
        this.value = img;
    }

}
