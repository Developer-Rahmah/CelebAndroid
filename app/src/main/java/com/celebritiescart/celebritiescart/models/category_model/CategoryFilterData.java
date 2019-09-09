package com.celebritiescart.celebritiescart.models.category_model;

import com.celebritiescart.celebritiescart.models.product_model.SearchCriteria;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterData {

    @SerializedName("items")
    @Expose
    private List<CategoryDetails> items = null;
    private List<CategoryDetails> finalItems = new ArrayList<>();

    @SerializedName("search_criteria")
    @Expose
    private SearchCriteria searchCriteria;
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;

    /**
     * No args constructor for use in serialization
     *
     */
    public CategoryFilterData() {
    }

    /**
     *
     * @param totalCount
     * @param searchCriteria
     * @param items
     */
    public CategoryFilterData(List<CategoryDetails> items, SearchCriteria searchCriteria, Integer totalCount) {
        super();
        this.items = items;
        this.searchCriteria = searchCriteria;
        this.totalCount = totalCount;
    }

    public List<CategoryDetails> getItems() {

        return items;
    }
// public List<CategoryDetails> getItems() {
//        for(int i=0;i<items.size();i++){
//            if(items.get(i).getIs_active()){
//                this.finalItems.add(items.get(i));
//            }}
//        return finalItems;
//    }

    public void setItems(List<CategoryDetails> items) {

     this.items = items;
//        for (int i=0;i<=items.size();i++){
//            if(items.get(i).getIs_active()){
//                this.items.add(items.get(i));
//            }
//        }
    }

    public CategoryFilterData withItems(List<CategoryDetails> items) {
        this.items = items;
        return this;
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public CategoryFilterData withSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
        return this;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public CategoryFilterData withTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

}