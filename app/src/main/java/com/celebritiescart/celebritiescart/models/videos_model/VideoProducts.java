package com.celebritiescart.celebritiescart.models.videos_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoProducts {

    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("related_id")
    @Expose
    private String relatedId;
    @SerializedName("position")
    @Expose
    private String position;

    /**
     * No args constructor for use in serialization
     *
     */
    public VideoProducts() {
    }

    /**
     *
     * @param position
     * @param relatedId
     * @param postId
     */
    public VideoProducts(String postId, String relatedId, String position) {
        super();
        this.postId = postId;
        this.relatedId = relatedId;
        this.position = position;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}