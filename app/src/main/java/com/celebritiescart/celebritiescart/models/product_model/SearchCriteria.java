
package com.celebritiescart.celebritiescart.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchCriteria {

    @SerializedName("filter_groups")
    @Expose
    private List<FilterGroup> filterGroups = null;
    @SerializedName("sort_orders")
    @Expose
    private List<SortOrder> sortOrders = null;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    @SerializedName("current_page")
    @Expose
    private Integer currentPage;

    public List<FilterGroup> getFilterGroups() {
        return filterGroups;
    }

    public void setFilterGroups(List<FilterGroup> filterGroups) {
        this.filterGroups = filterGroups;
    }

    public List<SortOrder> getSortOrders() {
        return sortOrders;
    }

    public void setSortOrders(List<SortOrder> sortOrders) {
        this.sortOrders = sortOrders;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

}
