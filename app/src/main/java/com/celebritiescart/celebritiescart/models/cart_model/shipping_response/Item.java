
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {

    @SerializedName("item_id")
    @Expose
    private Double itemId;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("base_price")
    @Expose
    private Double basePrice;
    @SerializedName("qty")
    @Expose
    private Double qty;
    @SerializedName("row_total")
    @Expose
    private Double rowTotal;
    @SerializedName("base_row_total")
    @Expose
    private Double baseRowTotal;
    @SerializedName("row_total_with_discount")
    @Expose
    private Double rowTotalWithDiscount;
    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("base_tax_amount")
    @Expose
    private Double baseTaxAmount;
    @SerializedName("tax_percent")
    @Expose
    private Double taxPercent;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("base_discount_amount")
    @Expose
    private Double baseDiscountAmount;
    @SerializedName("discount_percent")
    @Expose
    private Double discountPercent;
    @SerializedName("price_incl_tax")
    @Expose
    private Double priceInclTax;
    @SerializedName("base_price_incl_tax")
    @Expose
    private Double basePriceInclTax;
    @SerializedName("row_total_incl_tax")
    @Expose
    private Double rowTotalInclTax;
    @SerializedName("base_row_total_incl_tax")
    @Expose
    private Double baseRowTotalInclTax;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("weee_tax_applied_amount")
    @Expose
    private Double weeeTaxAppliedAmount;
    @SerializedName("weee_tax_applied")
    @Expose
    private String weeeTaxApplied;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param priceInclTax
     * @param baseRowTotalInclTax
     * @param baseRowTotal
     * @param extensionAttributes
     * @param qty
     * @param itemId
     * @param discountAmount
     * @param weeeTaxApplied
     * @param rowTotalInclTax
     * @param taxAmount
     * @param price
     * @param baseTaxAmount
     * @param name
     * @param rowTotalWithDiscount
     * @param weeeTaxAppliedAmount
     * @param basePriceInclTax
     * @param rowTotal
     * @param discountPercent
     * @param taxPercent
     * @param baseDiscountAmount
     * @param basePrice
     * @param options
     */
    public Item(Double itemId, Double price, Double basePrice, Double qty, Double rowTotal, Double baseRowTotal, Double rowTotalWithDiscount, Double taxAmount, Double baseTaxAmount, Double taxPercent, Double discountAmount, Double baseDiscountAmount, Double discountPercent, Double priceInclTax, Double basePriceInclTax, Double rowTotalInclTax, Double baseRowTotalInclTax, String options, Double weeeTaxAppliedAmount, String weeeTaxApplied, ExtensionAttributes extensionAttributes, String name) {
        super();
        this.itemId = itemId;
        this.price = price;
        this.basePrice = basePrice;
        this.qty = qty;
        this.rowTotal = rowTotal;
        this.baseRowTotal = baseRowTotal;
        this.rowTotalWithDiscount = rowTotalWithDiscount;
        this.taxAmount = taxAmount;
        this.baseTaxAmount = baseTaxAmount;
        this.taxPercent = taxPercent;
        this.discountAmount = discountAmount;
        this.baseDiscountAmount = baseDiscountAmount;
        this.discountPercent = discountPercent;
        this.priceInclTax = priceInclTax;
        this.basePriceInclTax = basePriceInclTax;
        this.rowTotalInclTax = rowTotalInclTax;
        this.baseRowTotalInclTax = baseRowTotalInclTax;
        this.options = options;
        this.weeeTaxAppliedAmount = weeeTaxAppliedAmount;
        this.weeeTaxApplied = weeeTaxApplied;
        this.extensionAttributes = extensionAttributes;
        this.name = name;
    }

    public Double getItemId() {
        return itemId;
    }

    public void setItemId(Double itemId) {
        this.itemId = itemId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(Double rowTotal) {
        this.rowTotal = rowTotal;
    }

    public Double getBaseRowTotal() {
        return baseRowTotal;
    }

    public void setBaseRowTotal(Double baseRowTotal) {
        this.baseRowTotal = baseRowTotal;
    }

    public Double getRowTotalWithDiscount() {
        return rowTotalWithDiscount;
    }

    public void setRowTotalWithDiscount(Double rowTotalWithDiscount) {
        this.rowTotalWithDiscount = rowTotalWithDiscount;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getBaseTaxAmount() {
        return baseTaxAmount;
    }

    public void setBaseTaxAmount(Double baseTaxAmount) {
        this.baseTaxAmount = baseTaxAmount;
    }

    public Double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(Double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getBaseDiscountAmount() {
        return baseDiscountAmount;
    }

    public void setBaseDiscountAmount(Double baseDiscountAmount) {
        this.baseDiscountAmount = baseDiscountAmount;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getPriceInclTax() {
        return priceInclTax;
    }

    public void setPriceInclTax(Double priceInclTax) {
        this.priceInclTax = priceInclTax;
    }

    public Double getBasePriceInclTax() {
        return basePriceInclTax;
    }

    public void setBasePriceInclTax(Double basePriceInclTax) {
        this.basePriceInclTax = basePriceInclTax;
    }

    public Double getRowTotalInclTax() {
        return rowTotalInclTax;
    }

    public void setRowTotalInclTax(Double rowTotalInclTax) {
        this.rowTotalInclTax = rowTotalInclTax;
    }

    public Double getBaseRowTotalInclTax() {
        return baseRowTotalInclTax;
    }

    public void setBaseRowTotalInclTax(Double baseRowTotalInclTax) {
        this.baseRowTotalInclTax = baseRowTotalInclTax;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Double getWeeeTaxAppliedAmount() {
        return weeeTaxAppliedAmount;
    }

    public void setWeeeTaxAppliedAmount(Double weeeTaxAppliedAmount) {
        this.weeeTaxAppliedAmount = weeeTaxAppliedAmount;
    }

    public String getWeeeTaxApplied() {
        return weeeTaxApplied;
    }

    public void setWeeeTaxApplied(String weeeTaxApplied) {
        this.weeeTaxApplied = weeeTaxApplied;
    }

    public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
