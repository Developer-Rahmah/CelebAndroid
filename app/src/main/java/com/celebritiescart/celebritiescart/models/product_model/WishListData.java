package com.celebritiescart.celebritiescart.models.product_model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListData
{

    @SerializedName("wishlist_item_id")
    @Expose
    private String wishlistItemId;
    @SerializedName("wishlist_id")
    @Expose
    private String wishlistId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("added_at")
    @Expose
    private String addedAt;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("product")
    @Expose
    private ProductDetails product;

    /**
     * No args constructor for use in serialization
     *
     */
    public WishListData() {
    }

    /**
     *
     * @param product
     * @param description
     * @param addedAt
     * @param wishlistItemId
     * @param qty
     * @param storeId
     * @param productId
     * @param wishlistId
     */
    public WishListData(String wishlistItemId, String wishlistId, String productId, String storeId, String addedAt, String description, Integer qty, ProductDetails product) {
        super();
        this.wishlistItemId = wishlistItemId;
        this.wishlistId = wishlistId;
        this.productId = productId;
        this.storeId = storeId;
        this.addedAt = addedAt;
        this.description = description;
        this.qty = qty;
        this.product = product;
    }

    public String getWishlistItemId() {
        return wishlistItemId;
    }

    public void setWishlistItemId(String wishlistItemId) {
        this.wishlistItemId = wishlistItemId;
    }

    public WishListData withWishlistItemId(String wishlistItemId) {
        this.wishlistItemId = wishlistItemId;
        return this;
    }

    public String getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
    }

    public WishListData withWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public WishListData withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public WishListData withStoreId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public WishListData withAddedAt(String addedAt) {
        this.addedAt = addedAt;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WishListData withDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public WishListData withQty(Integer qty) {
        this.qty = qty;
        return this;
    }

    public ProductDetails getProduct() {
        product.setWishlist_item_id(this.wishlistItemId);
        product.setId(Integer.valueOf(this.productId));
        return product;
    }

    public void setProduct(ProductDetails product) {
        this.product = product;
    }

    public WishListData withProduct(ProductDetails product) {
        this.product = product;
        return this;
    }

}