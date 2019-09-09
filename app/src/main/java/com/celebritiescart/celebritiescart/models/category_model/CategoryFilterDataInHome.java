package com.celebritiescart.celebritiescart.models.category_model;

import com.celebritiescart.celebritiescart.models.product_model.SearchCriteria;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterDataInHome {

    @SerializedName("items")
    @Expose
    private List<CategoryDetailsInHome> items = null;
    private List<CategoryDetailsInHome> finalItems = new ArrayList<>();

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
    public CategoryFilterDataInHome() {
    }

    /**
     *
     * @param totalCount
     * @param searchCriteria
     * @param items
     */
    public CategoryFilterDataInHome(List<CategoryDetailsInHome> items, SearchCriteria searchCriteria, Integer totalCount) {
        super();
        this.items = items;
        this.searchCriteria = searchCriteria;
        this.totalCount = totalCount;
    }

    public List<CategoryDetailsInHome> getItems() {

        return items;
    }
// public List<CategoryDetails> getItems() {
//        for(int i=0;i<items.size();i++){
//            if(items.get(i).getIs_active()){
//                this.finalItems.add(items.get(i));
//            }}
//        return finalItems;
//    }

    public void setItems(List<CategoryDetailsInHome> items) {

     this.items = items;
//        for (int i=0;i<=items.size();i++){
//            if(items.get(i).getIs_active()){
//                this.items.add(items.get(i));
//            }
//        }
    }

    public CategoryFilterDataInHome withItems(List<CategoryDetailsInHome> items) {
        this.items = items;
        return this;
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public CategoryFilterDataInHome withSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
        return this;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public CategoryFilterDataInHome withTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

}