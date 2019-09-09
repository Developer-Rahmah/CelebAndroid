package com.celebritiescart.celebritiescart.customer_reg;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExtensionAttributes implements Serializable {

    @SerializedName("socialId")
    @Expose
    private String social_id;
    @SerializedName("socialType")
    @Expose
    private String social_type;

    @SerializedName("social_id")
    @Expose
    private String socialId;
    @SerializedName("social_type")
    @Expose
    private String socialType;

    /**
     * No args constructor for use in serialization
     *
     * @param social_id
     * @param social_type
     */
    public ExtensionAttributes(String social_id, String social_type) {
        this.social_id = social_id;
        this.social_type = social_type;
        this.socialId = social_id;
        this.socialType = social_type;
    }

    public String getSocial_type() {
        return social_type;
    }

    public void setSocial_type(String social_type) {
        this.social_type = social_type;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

}
