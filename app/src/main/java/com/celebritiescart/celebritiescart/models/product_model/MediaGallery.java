package com.celebritiescart.celebritiescart.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class MediaGallery {

    @SerializedName("images")
    @Expose
    public JSONObject images;
    @SerializedName("values")
    @Expose
    public List<Object> values = null;

    public MediaGallery withImages(JSONObject images) {
        this.images = images;
        return this;
    }

    public MediaGallery withValues(List<Object> values) {
        this.values = values;
        return this;
    }

}