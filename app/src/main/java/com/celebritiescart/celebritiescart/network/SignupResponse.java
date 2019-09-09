package com.celebritiescart.celebritiescart.network;


import com.celebritiescart.celebritiescart.models.product_model.CustomAttribute;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignupResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_in")
    @Expose
    private String createdIn;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("website_id")
    @Expose
    private Integer websiteId;
    @SerializedName("addresses")
    @Expose
    private List<Object> addresses = null;
    @SerializedName("disable_auto_group_change")
    @Expose
    private Integer disableAutoGroupChange;
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes = null;


    /**
     * No args constructor for use in serialization
     *
     */
    public SignupResponse() {
    }

    /**
     *
     * @param lastname
     * @param firstname
     * @param disableAutoGroupChange
     * @param id
     * @param updatedAt
     * @param groupId
     * @param websiteId
     * @param email
     * @param createdAt
     * @param createdIn
     * @param confirmation
     * @param addresses
     * @param storeId
     */
    public SignupResponse(Integer id, Integer groupId, String confirmation, String createdAt, String updatedAt, String createdIn, String email, String firstname, String lastname, Integer storeId, Integer websiteId, List<Object> addresses, Integer disableAutoGroupChange) {
        super();
        this.id = id;
        this.groupId = groupId;
        this.confirmation = confirmation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdIn = createdIn;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.storeId = storeId;
        this.websiteId = websiteId;
        this.addresses = addresses;
        this.disableAutoGroupChange = disableAutoGroupChange;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(String createdIn) {
        this.createdIn = createdIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    public List<Object> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Object> addresses) {
        this.addresses = addresses;
    }

    public Integer getDisableAutoGroupChange() {
        return disableAutoGroupChange;
    }

    public void setDisableAutoGroupChange(Integer disableAutoGroupChange) {
        this.disableAutoGroupChange = disableAutoGroupChange;
    }

}