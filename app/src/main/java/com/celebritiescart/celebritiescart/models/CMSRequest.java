package com.celebritiescart.celebritiescart.models;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CMSRequest implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("page_layout")
    @Expose
    private String pageLayout;
    @SerializedName("content_heading")
    @Expose
    private String contentHeading;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("creation_time")
    @Expose
    private String creationTime;
    @SerializedName("update_time")
    @Expose
    private String updateTime;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;
    @SerializedName("active")
    @Expose
    private Boolean active;
    private final static long serialVersionUID = -1257121669322524007L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CMSRequest() {
    }

    /**
     *
     * @param content
     * @param id
     * @param pageLayout
     * @param title
     * @param updateTime
     * @param sortOrder
     * @param active
     * @param contentHeading
     * @param creationTime
     * @param identifier
     */
    public CMSRequest(Integer id, String identifier, String title, String pageLayout, String contentHeading, String content, String creationTime, String updateTime, String sortOrder, Boolean active) {
        super();
        this.id = id;
        this.identifier = identifier;
        this.title = title;
        this.pageLayout = pageLayout;
        this.contentHeading = contentHeading;
        this.content = content;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
        this.sortOrder = sortOrder;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageLayout() {
        return pageLayout;
    }

    public void setPageLayout(String pageLayout) {
        this.pageLayout = pageLayout;
    }

    public String getContentHeading() {
        return contentHeading;
    }

    public void setContentHeading(String contentHeading) {
        this.contentHeading = contentHeading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}