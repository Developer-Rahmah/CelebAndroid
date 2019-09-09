
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TotalSegment implements Serializable {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes__ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TotalSegment() {
    }

    /**
     * 
     * @param title
     * @param area
     * @param extensionAttributes
     * @param value
     * @param code
     */
    public TotalSegment(String code, String title, Double value, String area, ExtensionAttributes__ extensionAttributes) {
        super();
        this.code = code;
        this.title = title;
        this.value = value;
        this.area = area;
        this.extensionAttributes = extensionAttributes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public ExtensionAttributes__ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes__ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
