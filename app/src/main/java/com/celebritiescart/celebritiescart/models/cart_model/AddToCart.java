package com.celebritiescart.celebritiescart.models.cart_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCart {

    @SerializedName("cartItem")
    @Expose
    private CartItem cartItem;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddToCart() {
    }

    /**
     *
     * @param cartItem
     */
    public AddToCart(CartItem cartItem) {
        super();
        this.cartItem = cartItem;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

}