
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartResponse {

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("quote_id")
    @Expose
    private String quoteId;
    @SerializedName("product_option")
    @Expose
    private ProductOption productOption;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes_____ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddToCartResponse() {
    }

    /**
     * 
     * @param price
     * @param quoteId
     * @param extensionAttributes
     * @param name
     * @param qty
     * @param sku
     * @param itemId
     * @param productOption
     * @param productType
     */
    public AddToCartResponse(Integer itemId, String sku, Integer qty, String name, Double price, String productType, String quoteId, ProductOption productOption, ExtensionAttributes_____ extensionAttributes) {
        super();
        this.itemId = itemId;
        this.sku = sku;
        this.qty = qty;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.quoteId = quoteId;
        this.productOption = productOption;
        this.extensionAttributes = extensionAttributes;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public ExtensionAttributes_____ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes_____ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
