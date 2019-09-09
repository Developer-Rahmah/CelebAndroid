
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Totals implements Serializable {

    @SerializedName("grand_total")
    @Expose
    private Double grandTotal;
    @SerializedName("base_grand_total")
    @Expose
    private Double baseGrandTotal;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;
    @SerializedName("base_subtotal")
    @Expose
    private Double baseSubtotal;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("base_discount_amount")
    @Expose
    private Double baseDiscountAmount;
    @SerializedName("subtotal_with_discount")
    @Expose
    private Double subtotalWithDiscount;
    @SerializedName("base_subtotal_with_discount")
    @Expose
    private Double baseSubtotalWithDiscount;
    @SerializedName("shipping_amount")
    @Expose
    private Double shippingAmount;
    @SerializedName("base_shipping_amount")
    @Expose
    private Double baseShippingAmount;
    @SerializedName("shipping_discount_amount")
    @Expose
    private Double shippingDiscountAmount;
    @SerializedName("base_shipping_discount_amount")
    @Expose
    private Double baseShippingDiscountAmount;
    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("base_tax_amount")
    @Expose
    private Double baseTaxAmount;
    @SerializedName("weee_tax_applied_amount")
    @Expose
    private Double weeeTaxAppliedAmount;
    @SerializedName("shipping_tax_amount")
    @Expose
    private Double shippingTaxAmount;
    @SerializedName("base_shipping_tax_amount")
    @Expose
    private Double baseShippingTaxAmount;
    @SerializedName("subtotal_incl_tax")
    @Expose
    private Double subtotalInclTax;
    @SerializedName("base_subtotal_incl_tax")
    @Expose
    private Double baseSubtotalInclTax;
    @SerializedName("shipping_incl_tax")
    @Expose
    private Double shippingInclTax;
    @SerializedName("base_shipping_incl_tax")
    @Expose
    private Double baseShippingInclTax;
    @SerializedName("base_currency_code")
    @Expose
    private String baseCurrencyCode;
    @SerializedName("quote_currency_code")
    @Expose
    private String quoteCurrencyCode;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("items_qty")
    @Expose
    private Double itemsQty;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("total_segments")
    @Expose
    private List<TotalSegment> totalSegments = null;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes___ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Totals() {
    }

    /**
     * 
     * @param baseShippingDiscountAmount
     * @param itemsQty
     * @param baseShippingAmount
     * @param extensionAttributes
     * @param baseGrandTotal
     * @param baseSubtotal
     * @param shippingInclTax
     * @param discountAmount
     * @param totalSegments
     * @param baseSubtotalWithDiscount
     * @param shippingDiscountAmount
     * @param shippingTaxAmount
     * @param grandTotal
     * @param couponCode
     * @param subtotal
     * @param baseSubtotalInclTax
     * @param baseCurrencyCode
     * @param shippingAmount
     * @param taxAmount
     * @param quoteCurrencyCode
     * @param baseTaxAmount
     * @param items
     * @param subtotalWithDiscount
     * @param weeeTaxAppliedAmount
     * @param baseDiscountAmount
     * @param baseShippingInclTax
     * @param subtotalInclTax
     * @param baseShippingTaxAmount
     */
    public Totals(Double grandTotal, Double baseGrandTotal, Double subtotal, Double baseSubtotal, Double discountAmount, Double baseDiscountAmount, Double subtotalWithDiscount, Double baseSubtotalWithDiscount, Double shippingAmount, Double baseShippingAmount, Double shippingDiscountAmount, Double baseShippingDiscountAmount, Double taxAmount, Double baseTaxAmount, Double weeeTaxAppliedAmount, Double shippingTaxAmount, Double baseShippingTaxAmount, Double subtotalInclTax, Double baseSubtotalInclTax, Double shippingInclTax, Double baseShippingInclTax, String baseCurrencyCode, String quoteCurrencyCode, String couponCode, Double itemsQty, List<Item> items, List<TotalSegment> totalSegments, ExtensionAttributes___ extensionAttributes) {
        super();
        this.grandTotal = grandTotal;
        this.baseGrandTotal = baseGrandTotal;
        this.subtotal = subtotal;
        this.baseSubtotal = baseSubtotal;
        this.discountAmount = discountAmount;
        this.baseDiscountAmount = baseDiscountAmount;
        this.subtotalWithDiscount = subtotalWithDiscount;
        this.baseSubtotalWithDiscount = baseSubtotalWithDiscount;
        this.shippingAmount = shippingAmount;
        this.baseShippingAmount = baseShippingAmount;
        this.shippingDiscountAmount = shippingDiscountAmount;
        this.baseShippingDiscountAmount = baseShippingDiscountAmount;
        this.taxAmount = taxAmount;
        this.baseTaxAmount = baseTaxAmount;
        this.weeeTaxAppliedAmount = weeeTaxAppliedAmount;
        this.shippingTaxAmount = shippingTaxAmount;
        this.baseShippingTaxAmount = baseShippingTaxAmount;
        this.subtotalInclTax = subtotalInclTax;
        this.baseSubtotalInclTax = baseSubtotalInclTax;
        this.shippingInclTax = shippingInclTax;
        this.baseShippingInclTax = baseShippingInclTax;
        this.baseCurrencyCode = baseCurrencyCode;
        this.quoteCurrencyCode = quoteCurrencyCode;
        this.couponCode = couponCode;
        this.itemsQty = itemsQty;
        this.items = items;
        this.totalSegments = totalSegments;
        this.extensionAttributes = extensionAttributes;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Double getBaseGrandTotal() {
        return baseGrandTotal;
    }

    public void setBaseGrandTotal(Double baseGrandTotal) {
        this.baseGrandTotal = baseGrandTotal;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getBaseSubtotal() {
        return baseSubtotal;
    }

    public void setBaseSubtotal(Double baseSubtotal) {
        this.baseSubtotal = baseSubtotal;
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

    public Double getSubtotalWithDiscount() {
        return subtotalWithDiscount;
    }

    public void setSubtotalWithDiscount(Double subtotalWithDiscount) {
        this.subtotalWithDiscount = subtotalWithDiscount;
    }

    public Double getBaseSubtotalWithDiscount() {
        return baseSubtotalWithDiscount;
    }

    public void setBaseSubtotalWithDiscount(Double baseSubtotalWithDiscount) {
        this.baseSubtotalWithDiscount = baseSubtotalWithDiscount;
    }

    public Double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(Double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public Double getBaseShippingAmount() {
        return baseShippingAmount;
    }

    public void setBaseShippingAmount(Double baseShippingAmount) {
        this.baseShippingAmount = baseShippingAmount;
    }

    public Double getShippingDiscountAmount() {
        return shippingDiscountAmount;
    }

    public void setShippingDiscountAmount(Double shippingDiscountAmount) {
        this.shippingDiscountAmount = shippingDiscountAmount;
    }

    public Double getBaseShippingDiscountAmount() {
        return baseShippingDiscountAmount;
    }

    public void setBaseShippingDiscountAmount(Double baseShippingDiscountAmount) {
        this.baseShippingDiscountAmount = baseShippingDiscountAmount;
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

    public Double getWeeeTaxAppliedAmount() {
        return weeeTaxAppliedAmount;
    }

    public void setWeeeTaxAppliedAmount(Double weeeTaxAppliedAmount) {
        this.weeeTaxAppliedAmount = weeeTaxAppliedAmount;
    }

    public Double getShippingTaxAmount() {
        return shippingTaxAmount;
    }

    public void setShippingTaxAmount(Double shippingTaxAmount) {
        this.shippingTaxAmount = shippingTaxAmount;
    }

    public Double getBaseShippingTaxAmount() {
        return baseShippingTaxAmount;
    }

    public void setBaseShippingTaxAmount(Double baseShippingTaxAmount) {
        this.baseShippingTaxAmount = baseShippingTaxAmount;
    }

    public Double getSubtotalInclTax() {
        return subtotalInclTax;
    }

    public void setSubtotalInclTax(Double subtotalInclTax) {
        this.subtotalInclTax = subtotalInclTax;
    }

    public Double getBaseSubtotalInclTax() {
        return baseSubtotalInclTax;
    }

    public void setBaseSubtotalInclTax(Double baseSubtotalInclTax) {
        this.baseSubtotalInclTax = baseSubtotalInclTax;
    }

    public Double getShippingInclTax() {
        return shippingInclTax;
    }

    public void setShippingInclTax(Double shippingInclTax) {
        this.shippingInclTax = shippingInclTax;
    }

    public Double getBaseShippingInclTax() {
        return baseShippingInclTax;
    }

    public void setBaseShippingInclTax(Double baseShippingInclTax) {
        this.baseShippingInclTax = baseShippingInclTax;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public String getQuoteCurrencyCode() {
        return quoteCurrencyCode;
    }

    public void setQuoteCurrencyCode(String quoteCurrencyCode) {
        this.quoteCurrencyCode = quoteCurrencyCode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Double getItemsQty() {
        return itemsQty;
    }

    public void setItemsQty(Double itemsQty) {
        this.itemsQty = itemsQty;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<TotalSegment> getTotalSegments() {
        return totalSegments;
    }

    public void setTotalSegments(List<TotalSegment> totalSegments) {
        this.totalSegments = totalSegments;
    }

    public ExtensionAttributes___ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes___ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
