
package com.celebritiescart.celebritiescart.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingVote {

    @SerializedName("vote_id")
    @Expose
    private String voteId;
    @SerializedName("option_id")
    @Expose
    private String optionId;
    @SerializedName("remote_ip")
    @Expose
    private String remoteIp;
    @SerializedName("remote_ip_long")
    @Expose
    private String remoteIpLong;
    @SerializedName("customer_id")
    @Expose
    private Object customerId;
    @SerializedName("entity_pk_value")
    @Expose
    private String entityPkValue;
    @SerializedName("rating_id")
    @Expose
    private String ratingId;
    @SerializedName("review_id")
    @Expose
    private String reviewId;
    @SerializedName("percent")
    @Expose
    private String percent;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("rating_code")
    @Expose
    private String ratingCode;
    @SerializedName("store_id")
    @Expose
    private String storeId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RatingVote() {
    }

    /**
     * 
     * @param remoteIpLong
     * @param optionId
     * @param percent
     * @param customerId
     * @param remoteIp
     * @param value
     * @param ratingCode
     * @param entityPkValue
     * @param reviewId
     * @param ratingId
     * @param storeId
     * @param voteId
     */
    public RatingVote(String voteId, String optionId, String remoteIp, String remoteIpLong, Object customerId, String entityPkValue, String ratingId, String reviewId, String percent, String value, String ratingCode, String storeId) {
        super();
        this.voteId = voteId;
        this.optionId = optionId;
        this.remoteIp = remoteIp;
        this.remoteIpLong = remoteIpLong;
        this.customerId = customerId;
        this.entityPkValue = entityPkValue;
        this.ratingId = ratingId;
        this.reviewId = reviewId;
        this.percent = percent;
        this.value = value;
        this.ratingCode = ratingCode;
        this.storeId = storeId;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getRemoteIpLong() {
        return remoteIpLong;
    }

    public void setRemoteIpLong(String remoteIpLong) {
        this.remoteIpLong = remoteIpLong;
    }

    public Object getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Object customerId) {
        this.customerId = customerId;
    }

    public String getEntityPkValue() {
        return entityPkValue;
    }

    public void setEntityPkValue(String entityPkValue) {
        this.entityPkValue = entityPkValue;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRatingCode() {
        return ratingCode;
    }

    public void setRatingCode(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

}
