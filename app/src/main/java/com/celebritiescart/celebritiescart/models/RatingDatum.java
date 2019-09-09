package com.celebritiescart.celebritiescart.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RatingDatum implements Serializable
{

    @SerializedName("rating_id")
    @Expose
    private String ratingId;
    @SerializedName("ratingCode")
    @Expose
    private String ratingCode;
    @SerializedName("ratingValue")
    @Expose
    private String ratingValue;
    private final static long serialVersionUID = 535151752945987079L;

    /**
     * No args constructor for use in serialization
     *
     */
    public RatingDatum() {
    }

    /**
     *
     * @param ratingValue
     * @param ratingCode
     * @param ratingId
     */
    public RatingDatum(String ratingId, String ratingCode, String ratingValue) {
        super();
        this.ratingId = ratingId;
        this.ratingCode = ratingCode;
        this.ratingValue = ratingValue;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getRatingCode() {
        return ratingCode;
    }

    public void setRatingCode(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

}