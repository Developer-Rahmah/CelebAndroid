package com.celebritiescart.celebritiescart.models.address_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZoneDetails {

    @SerializedName("id")
    @Expose
    private String zoneId;
    @SerializedName("code")
    @Expose
    private String zoneCode;
    @SerializedName("name")
    @Expose
    private String zoneName;

    /**
     * 
     * @return
     *     The zoneId
     */
    public String getZoneId() {
        return zoneId;
    }

    /**
     * 
     * @param zoneId
     *     The zone_id
     */
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }


    /**
     * 
     * @return
     *     The zoneCode
     */
    public String getZoneCode() {
        return zoneCode;
    }

    /**
     * 
     * @param zoneCode
     *     The zone_code
     */
    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    /**
     * 
     * @return
     *     The zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * @param zoneName
     *     The zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

}
