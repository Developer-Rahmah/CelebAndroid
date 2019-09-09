package com.celebritiescart.celebritiescart.models.user_model;

import com.celebritiescart.celebritiescart.models.cart_model.shipping_response.ExtensionAttributes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Region {

    @SerializedName("region_code")
    @Expose
    private String regionCode;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("region_id")
    @Expose
    private Integer regionId;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes;

    /**
     * No args constructor for use in serialization
     *
     */
    public Region() {
    }

    /**
     *
     * @param region
     * @param regionCode
     * @param extensionAttributes
     * @param regionId
     */
    public Region(String regionCode, String region, Integer regionId, ExtensionAttributes extensionAttributes) {
        super();
        this.regionCode = regionCode;
        this.region = region;
        this.regionId = regionId;
        this.extensionAttributes = extensionAttributes;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}