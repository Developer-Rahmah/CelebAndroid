
package com.celebritiescart.celebritiescart.models.product_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("review_id")
    @Expose
    private String reviewId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("entity_pk_value")
    @Expose
    private String entityPkValue;
    @SerializedName("status_id")
    @Expose
    private String statusId;
    @SerializedName("detail_id")
    @Expose
    private String detailId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("customer_id")
    @Expose
    private Object customerId;
    @SerializedName("entity_code")
    @Expose
    private String entityCode;
    @SerializedName("rating_votes")
    @Expose
    private List<RatingVote> ratingVotes = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Review() {
    }

    /**
     * 
     * @param statusId
     * @param entityCode
     * @param detail
     * @param title
     * @param customerId
     * @param ratingVotes
     * @param nickname
     * @param createdAt
     * @param entityId
     * @param entityPkValue
     * @param reviewId
     * @param detailId
     */
    public Review(String reviewId, String createdAt, String entityId, String entityPkValue, String statusId, String detailId, String title, String detail, String nickname, Object customerId, String entityCode, List<RatingVote> ratingVotes) {
        super();
        this.reviewId = reviewId;
        this.createdAt = createdAt;
        this.entityId = entityId;
        this.entityPkValue = entityPkValue;
        this.statusId = statusId;
        this.detailId = detailId;
        this.title = title;
        this.detail = detail;
        this.nickname = nickname;
        this.customerId = customerId;
        this.entityCode = entityCode;
        this.ratingVotes = ratingVotes;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityPkValue() {
        return entityPkValue;
    }

    public void setEntityPkValue(String entityPkValue) {
        this.entityPkValue = entityPkValue;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Object customerId) {
        this.customerId = customerId;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public List<RatingVote> getRatingVotes() {
        return ratingVotes;
    }

    public void setRatingVotes(List<RatingVote> ratingVotes) {
        this.ratingVotes = ratingVotes;
    }

}
