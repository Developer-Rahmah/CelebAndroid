package com.celebritiescart.celebritiescart.models.product_model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListResponse
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;

    /**
     * No args constructor for use in serialization
     *
     */
    public WishListResponse() {
    }

    /**
     *
     * @param message
     * @param status
     */
    public WishListResponse(String message, Boolean status) {
        super();
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WishListResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public WishListResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

}