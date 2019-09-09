package com.celebritiescart.celebritiescart.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProductData {


    @SerializedName("items")
    @Expose
    private List<ProductDetails> items = null;
    @SerializedName("search_criteria")
    @Expose
    private SearchCriteria searchCriteria;
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;

    public List<ProductDetails> getItems() {
        return items;
    }

    public void setItems(List<ProductDetails> items) {
        this.items = items;
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<ProductDetails>  getProductData() {
        return items;
    }
}
