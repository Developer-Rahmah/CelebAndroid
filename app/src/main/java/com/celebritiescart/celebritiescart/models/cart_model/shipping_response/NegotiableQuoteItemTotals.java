
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NegotiableQuoteItemTotals implements Serializable {

    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("catalog_price")
    @Expose
    private Integer catalogPrice;
    @SerializedName("base_catalog_price")
    @Expose
    private Integer baseCatalogPrice;
    @SerializedName("catalog_price_incl_tax")
    @Expose
    private Integer catalogPriceInclTax;
    @SerializedName("base_catalog_price_incl_tax")
    @Expose
    private Integer baseCatalogPriceInclTax;
    @SerializedName("cart_price")
    @Expose
    private Integer cartPrice;
    @SerializedName("base_cart_price")
    @Expose
    private Integer baseCartPrice;
    @SerializedName("cart_tax")
    @Expose
    private Integer cartTax;
    @SerializedName("base_cart_tax")
    @Expose
    private Integer baseCartTax;
    @SerializedName("cart_price_incl_tax")
    @Expose
    private Integer cartPriceInclTax;
    @SerializedName("base_cart_price_incl_tax")
    @Expose
    private Integer baseCartPriceInclTax;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes_ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NegotiableQuoteItemTotals() {
    }

    /**
     * 
     * @param baseCatalogPrice
     * @param cartTax
     * @param extensionAttributes
     * @param baseCartPrice
     * @param catalogPriceInclTax
     * @param baseCartPriceInclTax
     * @param baseCatalogPriceInclTax
     * @param catalogPrice
     * @param baseCartTax
     * @param cartPriceInclTax
     * @param cartPrice
     * @param cost
     */
    public NegotiableQuoteItemTotals(Integer cost, Integer catalogPrice, Integer baseCatalogPrice, Integer catalogPriceInclTax, Integer baseCatalogPriceInclTax, Integer cartPrice, Integer baseCartPrice, Integer cartTax, Integer baseCartTax, Integer cartPriceInclTax, Integer baseCartPriceInclTax, ExtensionAttributes_ extensionAttributes) {
        super();
        this.cost = cost;
        this.catalogPrice = catalogPrice;
        this.baseCatalogPrice = baseCatalogPrice;
        this.catalogPriceInclTax = catalogPriceInclTax;
        this.baseCatalogPriceInclTax = baseCatalogPriceInclTax;
        this.cartPrice = cartPrice;
        this.baseCartPrice = baseCartPrice;
        this.cartTax = cartTax;
        this.baseCartTax = baseCartTax;
        this.cartPriceInclTax = cartPriceInclTax;
        this.baseCartPriceInclTax = baseCartPriceInclTax;
        this.extensionAttributes = extensionAttributes;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getCatalogPrice() {
        return catalogPrice;
    }

    public void setCatalogPrice(Integer catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    public Integer getBaseCatalogPrice() {
        return baseCatalogPrice;
    }

    public void setBaseCatalogPrice(Integer baseCatalogPrice) {
        this.baseCatalogPrice = baseCatalogPrice;
    }

    public Integer getCatalogPriceInclTax() {
        return catalogPriceInclTax;
    }

    public void setCatalogPriceInclTax(Integer catalogPriceInclTax) {
        this.catalogPriceInclTax = catalogPriceInclTax;
    }

    public Integer getBaseCatalogPriceInclTax() {
        return baseCatalogPriceInclTax;
    }

    public void setBaseCatalogPriceInclTax(Integer baseCatalogPriceInclTax) {
        this.baseCatalogPriceInclTax = baseCatalogPriceInclTax;
    }

    public Integer getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(Integer cartPrice) {
        this.cartPrice = cartPrice;
    }

    public Integer getBaseCartPrice() {
        return baseCartPrice;
    }

    public void setBaseCartPrice(Integer baseCartPrice) {
        this.baseCartPrice = baseCartPrice;
    }

    public Integer getCartTax() {
        return cartTax;
    }

    public void setCartTax(Integer cartTax) {
        this.cartTax = cartTax;
    }

    public Integer getBaseCartTax() {
        return baseCartTax;
    }

    public void setBaseCartTax(Integer baseCartTax) {
        this.baseCartTax = baseCartTax;
    }

    public Integer getCartPriceInclTax() {
        return cartPriceInclTax;
    }

    public void setCartPriceInclTax(Integer cartPriceInclTax) {
        this.cartPriceInclTax = cartPriceInclTax;
    }

    public Integer getBaseCartPriceInclTax() {
        return baseCartPriceInclTax;
    }

    public void setBaseCartPriceInclTax(Integer baseCartPriceInclTax) {
        this.baseCartPriceInclTax = baseCartPriceInclTax;
    }

    public ExtensionAttributes_ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes_ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
