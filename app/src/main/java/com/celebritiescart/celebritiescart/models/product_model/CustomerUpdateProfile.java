package com.celebritiescart.celebritiescart.models.product_model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerUpdateProfile {

    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttributeUpdateProfile> customAttributes = null;
    final private Integer website_id = 1;
    final private Integer store_id = 1;

    /**
     * No args constructor for use in serialization
     *
     */
    public CustomerUpdateProfile() {
    }

    /**
     *
     * @param dob
     * @param lastname
     * @param firstname
     * @param customAttributes
     */
    public CustomerUpdateProfile(String email, String dob, String firstname, String lastname, List<CustomAttributeUpdateProfile> customAttributes) {
        super();
        this.dob = dob;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.customAttributes = customAttributes;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<CustomAttributeUpdateProfile> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttributeUpdateProfile> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWebsite_id() {
        return website_id;
    }

    public Integer getStore_id() {
        return store_id;
    }
}