package com.celebritiescart.celebritiescart.models.payment_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethod {

    @SerializedName("method")
    @Expose
    private String method;

    /**
     * No args constructor for use in serialization
     *
     */
    public PaymentMethod() {
    }

    /**
     *
     * @param method
     */
    public PaymentMethod(String method) {
        super();
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}