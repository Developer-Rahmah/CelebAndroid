package com.celebritiescart.celebritiescart.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class ReviewRequest implements Serializable
{

    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("ratingData")
    @Expose
    private List<RatingDatum> ratingData = null;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("storeId")
    @Expose
    private String storeId;
    private final static long serialVersionUID = -5269638553283302007L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ReviewRequest() {
    }

    /**
     *
     * @param detail
     * @param title
     * @param customerId
     * @param nickname
     * @param ratingData
     * @param storeId
     * @param productId
     */
    public ReviewRequest(String productId, String nickname, String title, String detail, List<RatingDatum> ratingData, String customerId, String storeId) {
        super();
        this.productId = productId;
        this.nickname = nickname;
        this.title = title;
        this.detail = detail;
        this.ratingData = ratingData;
        this.customerId = customerId;
        this.storeId = storeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<RatingDatum> getRatingData() {
        return ratingData;
    }

    public void setRatingData(List<RatingDatum> ratingData) {
        this.ratingData = ratingData;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

}