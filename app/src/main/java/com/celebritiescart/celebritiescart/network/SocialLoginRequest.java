package com.celebritiescart.celebritiescart.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLoginRequest {
    @SerializedName("socialId")
    @Expose
    public String social_id;
    @SerializedName("socialType")
    @Expose
    public String social_type;

    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("firstname")
    @Expose
    public String firstname;
    @SerializedName("lastname")
    @Expose
    public String lastname;

    public SocialLoginRequest(String social_id, String social_type,String email,String firstname,String lastname) {

        this.social_id = social_id;
        this.social_type = social_type;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
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
