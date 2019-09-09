
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileInfo {

    @SerializedName("base64_encoded_data")
    @Expose
    private String base64EncodedData;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FileInfo() {
    }

    /**
     * 
     * @param base64EncodedData
     * @param name
     * @param type
     */
    public FileInfo(String base64EncodedData, String type, String name) {
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
