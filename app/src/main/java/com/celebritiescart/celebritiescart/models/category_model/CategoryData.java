
package com.celebritiescart.celebritiescart.models.category_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CategoryData {

    @SerializedName("name")
    @Expose
    private String success;
    @SerializedName("children_data")
    @Expose
    private List<CategoryDetails> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_count")
    @Expose
    private Integer productCount;

    
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CategoryDetails> getData() {
        return data;
    }

    public void setData(List<CategoryDetails> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

}
