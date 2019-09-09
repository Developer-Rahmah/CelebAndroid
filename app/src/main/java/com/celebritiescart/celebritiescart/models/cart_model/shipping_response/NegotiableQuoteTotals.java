
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NegotiableQuoteTotals implements Serializable {

    @SerializedName("items_count")
    @Expose
    private Integer itemsCount;
    @SerializedName("quote_status")
    @Expose
    private String quoteStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("customer_group")
    @Expose
    private Integer customerGroup;
    @SerializedName("base_to_quote_rate")
    @Expose
    private Integer baseToQuoteRate;
    @SerializedName("cost_total")
    @Expose
    private Integer costTotal;
    @SerializedName("base_cost_total")
    @Expose
    private Integer baseCostTotal;
    @SerializedName("original_total")
    @Expose
    private Integer originalTotal;
    @SerializedName("base_original_total")
    @Expose
    private Integer baseOriginalTotal;
    @SerializedName("original_tax")
    @Expose
    private Integer originalTax;
    @SerializedName("base_original_tax")
    @Expose
    private Integer baseOriginalTax;
    @SerializedName("original_price_incl_tax")
    @Expose
    private Integer originalPriceInclTax;
    @SerializedName("base_original_price_incl_tax")
    @Expose
    private Integer baseOriginalPriceInclTax;
    @SerializedName("negotiated_price_type")
    @Expose
    private Integer negotiatedPriceType;
    @SerializedName("negotiated_price_value")
    @Expose
    private Integer negotiatedPriceValue;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NegotiableQuoteTotals() {
    }

    /**
     * 
     * @param originalTax
     * @param itemsCount
     * @param costTotal
     * @param baseOriginalTax
     * @param originalPriceInclTax
     * @param baseToQuoteRate
     * @param baseOriginalPriceInclTax
     * @param customerGroup
     * @param updatedAt
     * @param createdAt
     * @param baseCostTotal
     * @param baseOriginalTotal
     * @param negotiatedPriceValue
     * @param negotiatedPriceType
     * @param quoteStatus
     * @param originalTotal
     */
    public NegotiableQuoteTotals(Integer itemsCount, String quoteStatus, String createdAt, String updatedAt, Integer customerGroup, Integer baseToQuoteRate, Integer costTotal, Integer baseCostTotal, Integer originalTotal, Integer baseOriginalTotal, Integer originalTax, Integer baseOriginalTax, Integer originalPriceInclTax, Integer baseOriginalPriceInclTax, Integer negotiatedPriceType, Integer negotiatedPriceValue) {
        super();
        this.itemsCount = itemsCount;
        this.quoteStatus = quoteStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customerGroup = customerGroup;
        this.baseToQuoteRate = baseToQuoteRate;
        this.costTotal = costTotal;
        this.baseCostTotal = baseCostTotal;
        this.originalTotal = originalTotal;
        this.baseOriginalTotal = baseOriginalTotal;
        this.originalTax = originalTax;
        this.baseOriginalTax = baseOriginalTax;
        this.originalPriceInclTax = originalPriceInclTax;
        this.baseOriginalPriceInclTax = baseOriginalPriceInclTax;
        this.negotiatedPriceType = negotiatedPriceType;
        this.negotiatedPriceValue = negotiatedPriceValue;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    public String getQuoteStatus() {
        return quoteStatus;
    }

    public void setQuoteStatus(String quoteStatus) {
        this.quoteStatus = quoteStatus;
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

    public Integer getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(Integer customerGroup) {
        this.customerGroup = customerGroup;
    }

    public Integer getBaseToQuoteRate() {
        return baseToQuoteRate;
    }

    public void setBaseToQuoteRate(Integer baseToQuoteRate) {
        this.baseToQuoteRate = baseToQuoteRate;
    }

    public Integer getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Integer costTotal) {
        this.costTotal = costTotal;
    }

    public Integer getBaseCostTotal() {
        return baseCostTotal;
    }

    public void setBaseCostTotal(Integer baseCostTotal) {
        this.baseCostTotal = baseCostTotal;
    }

    public Integer getOriginalTotal() {
        return originalTotal;
    }

    public void setOriginalTotal(Integer originalTotal) {
        this.originalTotal = originalTotal;
    }

    public Integer getBaseOriginalTotal() {
        return baseOriginalTotal;
    }

    public void setBaseOriginalTotal(Integer baseOriginalTotal) {
        this.baseOriginalTotal = baseOriginalTotal;
    }

    public Integer getOriginalTax() {
        return originalTax;
    }

    public void setOriginalTax(Integer originalTax) {
        this.originalTax = originalTax;
    }

    public Integer getBaseOriginalTax() {
        return baseOriginalTax;
    }

    public void setBaseOriginalTax(Integer baseOriginalTax) {
        this.baseOriginalTax = baseOriginalTax;
    }

    public Integer getOriginalPriceInclTax() {
        return originalPriceInclTax;
    }

    public void setOriginalPriceInclTax(Integer originalPriceInclTax) {
        this.originalPriceInclTax = originalPriceInclTax;
    }

    public Integer getBaseOriginalPriceInclTax() {
        return baseOriginalPriceInclTax;
    }

    public void setBaseOriginalPriceInclTax(Integer baseOriginalPriceInclTax) {
        this.baseOriginalPriceInclTax = baseOriginalPriceInclTax;
    }

    public Integer getNegotiatedPriceType() {
        return negotiatedPriceType;
    }

    public void setNegotiatedPriceType(Integer negotiatedPriceType) {
        this.negotiatedPriceType = negotiatedPriceType;
    }

    public Integer getNegotiatedPriceValue() {
        return negotiatedPriceValue;
    }

    public void setNegotiatedPriceValue(Integer negotiatedPriceValue) {
        this.negotiatedPriceValue = negotiatedPriceValue;
    }

}
