package com.celebritiescart.celebritiescart.models.cart_model;

import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CartProduct {


    @SerializedName("customers_id")
    @Expose
    private int customersId;
    @SerializedName("customers_basket_id")
    @Expose
    private int customersBasketId;
    @SerializedName("customers_basket_date_added")
    @Expose
    private String customersBasketDateAdded;
    @SerializedName("customers_basket_product")
    @Expose
    private ProductDetails customersBasketProduct;
    @SerializedName("customers_basket_product_attributes")
    @Expose
    private List<CartProductAttributes> customersBasketProductAttributes = new ArrayList<CartProductAttributes>();
    @SerializedName("celebrityId")
    @Expose
    private String celebrityId;

    public String getCelebrityId() {
        return celebrityId;
    }

    public void setCelebrityId(String celebrityId) {
        this.celebrityId = celebrityId;
    }

    public int getCustomersId() {
        return customersId;
    }


    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    public int getCustomersBasketId() {
        return customersBasketId;
    }

    public void setCustomersBasketId(int customersBasketId) {
        this.customersBasketId = customersBasketId;
    }

    public String getCustomersBasketDateAdded() {
        return customersBasketDateAdded;
    }

    public void setCustomersBasketDateAdded(String customersBasketDateAdded) {
        this.customersBasketDateAdded = customersBasketDateAdded;
    }

    public ProductDetails getCustomersBasketProduct() {
        return customersBasketProduct;
    }

    public AddToCart getCartProduct(String quoteId) {
        return new AddToCart(new CartItem(getCustomersBasketProduct().getSku(), getCustomersBasketProduct().getCustomersBasketQuantity(), quoteId));
    }

    public void setCustomersBasketProduct(ProductDetails customersBasketProduct) {
        this.customersBasketProduct = customersBasketProduct;
    }

    public List<CartProductAttributes> getCustomersBasketProductAttributes() {
        return customersBasketProductAttributes;
    }

    public void setCustomersBasketProductAttributes(List<CartProductAttributes> customersBasketProductAttributes) {
        this.customersBasketProductAttributes = customersBasketProductAttributes;
    }

}
