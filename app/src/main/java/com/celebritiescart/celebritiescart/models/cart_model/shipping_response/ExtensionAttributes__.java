
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExtensionAttributes__ implements Serializable {

    @SerializedName("tax_grandtotal_details")
    @Expose
    private List<TaxGrandtotalDetail> taxGrandtotalDetails = null;
    @SerializedName("gift_cards")
    @Expose
    private String giftCards;
    @SerializedName("gw_order_id")
    @Expose
    private String gwOrderId;
    @SerializedName("gw_item_ids")
    @Expose
    private List<String> gwItemIds = null;
    @SerializedName("gw_allow_gift_receipt")
    @Expose
    private String gwAllowGiftReceipt;
    @SerializedName("gw_add_card")
    @Expose
    private String gwAddCard;
    @SerializedName("gw_price")
    @Expose
    private String gwPrice;
    @SerializedName("gw_base_price")
    @Expose
    private String gwBasePrice;
    @SerializedName("gw_items_price")
    @Expose
    private String gwItemsPrice;
    @SerializedName("gw_items_base_price")
    @Expose
    private String gwItemsBasePrice;
    @SerializedName("gw_card_price")
    @Expose
    private String gwCardPrice;
    @SerializedName("gw_card_base_price")
    @Expose
    private String gwCardBasePrice;
    @SerializedName("gw_base_tax_amount")
    @Expose
    private String gwBaseTaxAmount;
    @SerializedName("gw_tax_amount")
    @Expose
    private String gwTaxAmount;
    @SerializedName("gw_items_base_tax_amount")
    @Expose
    private String gwItemsBaseTaxAmount;
    @SerializedName("gw_items_tax_amount")
    @Expose
    private String gwItemsTaxAmount;
    @SerializedName("gw_card_base_tax_amount")
    @Expose
    private String gwCardBaseTaxAmount;
    @SerializedName("gw_card_tax_amount")
    @Expose
    private String gwCardTaxAmount;
    @SerializedName("gw_price_incl_tax")
    @Expose
    private String gwPriceInclTax;
    @SerializedName("gw_base_price_incl_tax")
    @Expose
    private String gwBasePriceInclTax;
    @SerializedName("gw_card_price_incl_tax")
    @Expose
    private String gwCardPriceInclTax;
    @SerializedName("gw_card_base_price_incl_tax")
    @Expose
    private String gwCardBasePriceInclTax;
    @SerializedName("gw_items_price_incl_tax")
    @Expose
    private String gwItemsPriceInclTax;
    @SerializedName("gw_items_base_price_incl_tax")
    @Expose
    private String gwItemsBasePriceInclTax;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExtensionAttributes__() {
    }

    /**
     * 
     * @param gwItemsBasePrice
     * @param gwBaseTaxAmount
     * @param gwPrice
     * @param gwItemsBasePriceInclTax
     * @param gwCardPriceInclTax
     * @param gwTaxAmount
     * @param gwCardTaxAmount
     * @param gwPriceInclTax
     * @param gwAddCard
     * @param gwBasePriceInclTax
     * @param gwAllowGiftReceipt
     * @param gwItemIds
     * @param gwItemsPriceInclTax
     * @param gwItemsPrice
     * @param gwCardBasePriceInclTax
     * @param taxGrandtotalDetails
     * @param gwItemsTaxAmount
     * @param giftCards
     * @param gwCardPrice
     * @param gwCardBasePrice
     * @param gwBasePrice
     * @param gwOrderId
     * @param gwCardBaseTaxAmount
     * @param gwItemsBaseTaxAmount
     */
    public ExtensionAttributes__(List<TaxGrandtotalDetail> taxGrandtotalDetails, String giftCards, String gwOrderId, List<String> gwItemIds, String gwAllowGiftReceipt, String gwAddCard, String gwPrice, String gwBasePrice, String gwItemsPrice, String gwItemsBasePrice, String gwCardPrice, String gwCardBasePrice, String gwBaseTaxAmount, String gwTaxAmount, String gwItemsBaseTaxAmount, String gwItemsTaxAmount, String gwCardBaseTaxAmount, String gwCardTaxAmount, String gwPriceInclTax, String gwBasePriceInclTax, String gwCardPriceInclTax, String gwCardBasePriceInclTax, String gwItemsPriceInclTax, String gwItemsBasePriceInclTax) {
        super();
        this.taxGrandtotalDetails = taxGrandtotalDetails;
        this.giftCards = giftCards;
        this.gwOrderId = gwOrderId;
        this.gwItemIds = gwItemIds;
        this.gwAllowGiftReceipt = gwAllowGiftReceipt;
        this.gwAddCard = gwAddCard;
        this.gwPrice = gwPrice;
        this.gwBasePrice = gwBasePrice;
        this.gwItemsPrice = gwItemsPrice;
        this.gwItemsBasePrice = gwItemsBasePrice;
        this.gwCardPrice = gwCardPrice;
        this.gwCardBasePrice = gwCardBasePrice;
        this.gwBaseTaxAmount = gwBaseTaxAmount;
        this.gwTaxAmount = gwTaxAmount;
        this.gwItemsBaseTaxAmount = gwItemsBaseTaxAmount;
        this.gwItemsTaxAmount = gwItemsTaxAmount;
        this.gwCardBaseTaxAmount = gwCardBaseTaxAmount;
        this.gwCardTaxAmount = gwCardTaxAmount;
        this.gwPriceInclTax = gwPriceInclTax;
        this.gwBasePriceInclTax = gwBasePriceInclTax;
        this.gwCardPriceInclTax = gwCardPriceInclTax;
        this.gwCardBasePriceInclTax = gwCardBasePriceInclTax;
        this.gwItemsPriceInclTax = gwItemsPriceInclTax;
        this.gwItemsBasePriceInclTax = gwItemsBasePriceInclTax;
    }

    public List<TaxGrandtotalDetail> getTaxGrandtotalDetails() {
        return taxGrandtotalDetails;
    }

    public void setTaxGrandtotalDetails(List<TaxGrandtotalDetail> taxGrandtotalDetails) {
        this.taxGrandtotalDetails = taxGrandtotalDetails;
    }

    public String getGiftCards() {
        return giftCards;
    }

    public void setGiftCards(String giftCards) {
        this.giftCards = giftCards;
    }

    public String getGwOrderId() {
        return gwOrderId;
    }

    public void setGwOrderId(String gwOrderId) {
        this.gwOrderId = gwOrderId;
    }

    public List<String> getGwItemIds() {
        return gwItemIds;
    }

    public void setGwItemIds(List<String> gwItemIds) {
        this.gwItemIds = gwItemIds;
    }

    public String getGwAllowGiftReceipt() {
        return gwAllowGiftReceipt;
    }

    public void setGwAllowGiftReceipt(String gwAllowGiftReceipt) {
        this.gwAllowGiftReceipt = gwAllowGiftReceipt;
    }

    public String getGwAddCard() {
        return gwAddCard;
    }

    public void setGwAddCard(String gwAddCard) {
        this.gwAddCard = gwAddCard;
    }

    public String getGwPrice() {
        return gwPrice;
    }

    public void setGwPrice(String gwPrice) {
        this.gwPrice = gwPrice;
    }

    public String getGwBasePrice() {
        return gwBasePrice;
    }

    public void setGwBasePrice(String gwBasePrice) {
        this.gwBasePrice = gwBasePrice;
    }

    public String getGwItemsPrice() {
        return gwItemsPrice;
    }

    public void setGwItemsPrice(String gwItemsPrice) {
        this.gwItemsPrice = gwItemsPrice;
    }

    public String getGwItemsBasePrice() {
        return gwItemsBasePrice;
    }

    public void setGwItemsBasePrice(String gwItemsBasePrice) {
        this.gwItemsBasePrice = gwItemsBasePrice;
    }

    public String getGwCardPrice() {
        return gwCardPrice;
    }

    public void setGwCardPrice(String gwCardPrice) {
        this.gwCardPrice = gwCardPrice;
    }

    public String getGwCardBasePrice() {
        return gwCardBasePrice;
    }

    public void setGwCardBasePrice(String gwCardBasePrice) {
        this.gwCardBasePrice = gwCardBasePrice;
    }

    public String getGwBaseTaxAmount() {
        return gwBaseTaxAmount;
    }

    public void setGwBaseTaxAmount(String gwBaseTaxAmount) {
        this.gwBaseTaxAmount = gwBaseTaxAmount;
    }

    public String getGwTaxAmount() {
        return gwTaxAmount;
    }

    public void setGwTaxAmount(String gwTaxAmount) {
        this.gwTaxAmount = gwTaxAmount;
    }

    public String getGwItemsBaseTaxAmount() {
        return gwItemsBaseTaxAmount;
    }

    public void setGwItemsBaseTaxAmount(String gwItemsBaseTaxAmount) {
        this.gwItemsBaseTaxAmount = gwItemsBaseTaxAmount;
    }

    public String getGwItemsTaxAmount() {
        return gwItemsTaxAmount;
    }

    public void setGwItemsTaxAmount(String gwItemsTaxAmount) {
        this.gwItemsTaxAmount = gwItemsTaxAmount;
    }

    public String getGwCardBaseTaxAmount() {
        return gwCardBaseTaxAmount;
    }

    public void setGwCardBaseTaxAmount(String gwCardBaseTaxAmount) {
        this.gwCardBaseTaxAmount = gwCardBaseTaxAmount;
    }

    public String getGwCardTaxAmount() {
        return gwCardTaxAmount;
    }

    public void setGwCardTaxAmount(String gwCardTaxAmount) {
        this.gwCardTaxAmount = gwCardTaxAmount;
    }

    public String getGwPriceInclTax() {
        return gwPriceInclTax;
    }

    public void setGwPriceInclTax(String gwPriceInclTax) {
        this.gwPriceInclTax = gwPriceInclTax;
    }

    public String getGwBasePriceInclTax() {
        return gwBasePriceInclTax;
    }

    public void setGwBasePriceInclTax(String gwBasePriceInclTax) {
        this.gwBasePriceInclTax = gwBasePriceInclTax;
    }

    public String getGwCardPriceInclTax() {
        return gwCardPriceInclTax;
    }

    public void setGwCardPriceInclTax(String gwCardPriceInclTax) {
        this.gwCardPriceInclTax = gwCardPriceInclTax;
    }

    public String getGwCardBasePriceInclTax() {
        return gwCardBasePriceInclTax;
    }

    public void setGwCardBasePriceInclTax(String gwCardBasePriceInclTax) {
        this.gwCardBasePriceInclTax = gwCardBasePriceInclTax;
    }

    public String getGwItemsPriceInclTax() {
        return gwItemsPriceInclTax;
    }

    public void setGwItemsPriceInclTax(String gwItemsPriceInclTax) {
        this.gwItemsPriceInclTax = gwItemsPriceInclTax;
    }

    public String getGwItemsBasePriceInclTax() {
        return gwItemsBasePriceInclTax;
    }

    public void setGwItemsBasePriceInclTax(String gwItemsBasePriceInclTax) {
        this.gwItemsBasePriceInclTax = gwItemsBasePriceInclTax;
    }

}
