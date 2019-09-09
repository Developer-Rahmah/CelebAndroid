package com.celebritiescart.celebritiescart.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EncodedImage {

    @SerializedName("base64EncodedData")
    @Expose
    private String base64EncodedData;
    @SerializedName("type")
    @Expose
    private String type="image/png";
    @SerializedName("name")
    @Expose
    private String name = "profile_image.png";

    /**
     * No args constructor for use in serialization
     *
     */
    public EncodedImage() {
    }

    /**
     *
     * @param base64EncodedData
     * @param name
     * @param type
     */
    public EncodedImage(String base64EncodedData, String type, String name) {
        super();
        this.base64EncodedData = base64EncodedData;
        this.type = type;
        this.name = name;
    }

    public String getBase64EncodedData() {
        return base64EncodedData;
    }

    public void setBase64EncodedData(String base64EncodedData) {
        this.base64EncodedData = base64EncodedData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}