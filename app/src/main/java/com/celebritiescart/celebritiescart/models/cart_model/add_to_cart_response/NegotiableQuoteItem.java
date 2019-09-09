
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NegotiableQuoteItem {

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("original_price")
    @Expose
    private Integer originalPrice;
    @SerializedName("original_tax_amount")
    @Expose
    private Integer originalTaxAmount;
    @SerializedName("original_discount_amount")
    @Expose
    private Integer originalDiscountAmount;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes______ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NegotiableQuoteItem() {
    }

    /**
     * 
     * @param originalTaxAmount
     * @param originalPrice
     * @param extensionAttributes
     * @param itemId
     * @param originalDiscountAmount
     */
    public NegotiableQuoteItem(Integer itemId, Integer originalPrice, Integer originalTaxAmount, Integer originalDiscountAmount, ExtensionAttributes______ extensionAttributes) {
        super();
        this.itemId = itemId;
        this.originalPrice = originalPrice;
        this.originalTaxAmount = originalTaxAmount;
        this.originalDiscountAmount = originalDiscountAmount;
        this.extensionAttributes = extensionAttributes;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getOriginalTaxAmount() {
        return originalTaxAmount;
    }

    public void setOriginalTaxAmount(Integer originalTaxAmount) {
        this.originalTaxAmount = originalTaxAmount;
    }

    public Integer getOriginalDiscountAmount() {
        return originalDiscountAmount;
    }

    public void setOriginalDiscountAmount(Integer originalDiscountAmount) {
        this.originalDiscountAmount = originalDiscountAmount;
    }

    public ExtensionAttributes______ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes______ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
