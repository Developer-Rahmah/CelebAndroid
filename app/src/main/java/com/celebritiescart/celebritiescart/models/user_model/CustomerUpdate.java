package com.celebritiescart.celebritiescart.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerUpdate {

    @SerializedName("customer")
    @Expose
    private Customer customer;

    /**
     * No args constructor for use in serialization
     *
     */
    public CustomerUpdate() {
    }

    /**
     *
     * @param customer
     */
    public CustomerUpdate(Customer customer) {
        super();
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}