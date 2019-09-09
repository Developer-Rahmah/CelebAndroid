
package com.celebritiescart.celebritiescart.models.product_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRating {

    @SerializedName("avg_rating_percent")
    @Expose
    private String avgRatingPercent;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductRating() {
    }

    /**
     * 
     * @param reviews
     * @param avgRatingPercent
     * @param count
     */
    public ProductRating(String avgRatingPercent, Integer count, List<Review> reviews) {
        super();
        this.avgRatingPercent = avgRatingPercent;
        this.count = count;
        this.reviews = reviews;
    }

    public String getAvgRatingPercent() {
        return avgRatingPercent;
    }

    public void setAvgRatingPercent(String avgRatingPercent) {
        this.avgRatingPercent = avgRatingPercent;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
