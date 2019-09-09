
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TaxGrandtotalDetail implements Serializable {

    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("rates")
    @Expose
    private List<Rate> rates = null;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TaxGrandtotalDetail() {
    }

    /**
     * 
     * @param amount
     * @param groupId
     * @param rates
     */
    public TaxGrandtotalDetail(Integer amount, List<Rate> rates, Integer groupId) {
        super();
        this.amount = amount;
        this.rates = rates;
        this.groupId = groupId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

}
