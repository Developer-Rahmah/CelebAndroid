package com.celebritiescart.celebritiescart.models.videos_model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoListing {

    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;
    @SerializedName("total_number")
    @Expose
    private Integer totalNumber;
    @SerializedName("current_page")
    @Expose
    private Integer currentPage;
    @SerializedName("last_page")
    @Expose
    private Integer lastPage;

    /**
     * No args constructor for use in serialization
     *
     */
    public VideoListing() {
    }

    /**
     *
     * @param lastPage
     * @param totalNumber
     * @param posts
     * @param currentPage
     */
    public VideoListing(List<Post> posts, Integer totalNumber, Integer currentPage, Integer lastPage) {
        super();
        this.posts = posts;
        this.totalNumber = totalNumber;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

}