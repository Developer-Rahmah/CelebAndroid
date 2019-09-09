package com.celebritiescart.celebritiescart.models.videos_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Post implements Parcelable
{

    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("meta_title")
    @Expose
    private Object metaTitle;
    @SerializedName("meta_keywords")
    @Expose
    private String metaKeywords;
    @SerializedName("meta_description")
    @Expose
    private String metaDescription;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("og_title")
    @Expose
    private Object ogTitle;
    @SerializedName("og_description")
    @Expose
    private Object ogDescription;
    @SerializedName("og_img")
    @Expose
    private Object ogImg;
    @SerializedName("og_type")
    @Expose
    private Object ogType;
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
    @SerializedName("publish_time")
    @Expose
    private String publishTime;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("featured_img")
    @Expose
    private String featuredImg;
    @SerializedName("author_id")
    @Expose
    private String authorId;
    @SerializedName("page_layout")
    @Expose
    private String pageLayout;
    @SerializedName("layout_update_xml")
    @Expose
    private Object layoutUpdateXml;
    @SerializedName("custom_theme")
    @Expose
    private Object customTheme;
    @SerializedName("custom_layout")
    @Expose
    private Object customLayout;
    @SerializedName("custom_layout_update_xml")
    @Expose
    private Object customLayoutUpdateXml;
    @SerializedName("custom_theme_from")
    @Expose
    private Object customThemeFrom;
    @SerializedName("custom_theme_to")
    @Expose
    private Object customThemeTo;
    @SerializedName("media_gallery")
    @Expose
    private Object mediaGallery;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("views_count")
    @Expose
    private Object viewsCount;
    @SerializedName("is_recent_posts_skip")
    @Expose
    private Object isRecentPostsSkip;
    @SerializedName("short_content")
    @Expose
    private Object shortContent;
    @SerializedName("_first_store_id")
    @Expose
    private List<String> firstStoreId = null;
    @SerializedName("store_ids")
    @Expose
    private List<String> storeIds = null;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("og_image")
    @Expose
    private Boolean ogImage;
    @SerializedName("short_filtered_content")
    @Expose
    private String shortFilteredContent;
    @SerializedName("filtered_content")
    @Expose
    private String filteredContent;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("first_image")
    @Expose
    private String firstImage;
    @SerializedName("post_url")
    @Expose
    private String postUrl;
    public final static Parcelable.Creator<Post> CREATOR = new Creator<Post>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return (new Post[size]);
        }

    }
            ;

    protected Post(Parcel in) {
        this.postId = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.metaTitle = in.readValue((Object.class.getClassLoader()));
        this.metaKeywords = ((String) in.readValue((String.class.getClassLoader())));
        this.metaDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.identifier = ((String) in.readValue((String.class.getClassLoader())));
        this.ogTitle = in.readValue((Object.class.getClassLoader()));
        this.ogDescription = in.readValue((Object.class.getClassLoader()));
        this.ogImg = in.readValue((Object.class.getClassLoader()));
        this.ogType = in.readValue((Object.class.getClassLoader()));
        this.contentHeading = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.creationTime = ((String) in.readValue((String.class.getClassLoader())));
        this.updateTime = ((String) in.readValue((String.class.getClassLoader())));
        this.publishTime = ((String) in.readValue((String.class.getClassLoader())));
        this.isActive = ((String) in.readValue((String.class.getClassLoader())));
        this.featuredImg = ((String) in.readValue((String.class.getClassLoader())));
        this.authorId = ((String) in.readValue((String.class.getClassLoader())));
        this.pageLayout = ((String) in.readValue((String.class.getClassLoader())));
        this.layoutUpdateXml = in.readValue((Object.class.getClassLoader()));
        this.customTheme = in.readValue((Object.class.getClassLoader()));
        this.customLayout = in.readValue((Object.class.getClassLoader()));
        this.customLayoutUpdateXml = in.readValue((Object.class.getClassLoader()));
        this.customThemeFrom = in.readValue((Object.class.getClassLoader()));
        this.customThemeTo = in.readValue((Object.class.getClassLoader()));
        this.mediaGallery = in.readValue((Object.class.getClassLoader()));
        this.secret = ((String) in.readValue((String.class.getClassLoader())));
        this.viewsCount = in.readValue((Object.class.getClassLoader()));
        this.isRecentPostsSkip = in.readValue((Object.class.getClassLoader()));
        this.shortContent = in.readValue((Object.class.getClassLoader()));
        in.readList(this.firstStoreId, (java.lang.String.class.getClassLoader()));
        in.readList(this.storeIds, (java.lang.String.class.getClassLoader()));
        this.storeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.categories, (java.lang.String.class.getClassLoader()));
        this.ogImage = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.shortFilteredContent = ((String) in.readValue((String.class.getClassLoader())));
        this.filteredContent = ((String) in.readValue((String.class.getClassLoader())));
        this.featuredImage = ((String) in.readValue((String.class.getClassLoader())));
        this.firstImage = ((String) in.readValue((String.class.getClassLoader())));
        this.postUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Post() {
    }

    /**
     *
     * @param mediaGallery
     * @param ogImg
     * @param metaKeywords
     * @param metaTitle
     * @param featuredImg
     * @param ogType
     * @param authorId
     * @param title
     * @param ogTitle
     * @param firstImage
     * @param contentHeading
     * @param creationTime
     * @param firstStoreId
     * @param shortFilteredContent
     * @param storeIds
     * @param customThemeTo
     * @param pageLayout
     * @param publishTime
     * @param isRecentPostsSkip
     * @param updateTime
     * @param postUrl
     * @param metaDescription
     * @param ogDescription
     * @param customThemeFrom
     * @param postId
     * @param layoutUpdateXml
     * @param customLayoutUpdateXml
     * @param content
     * @param isActive
     * @param customTheme
     * @param ogImage
     * @param shortContent
     * @param secret
     * @param categories
     * @param filteredContent
     * @param viewsCount
     * @param storeId
     * @param identifier
     * @param customLayout
     * @param featuredImage
     */
    public Post(String postId, String title, Object metaTitle, String metaKeywords, String metaDescription, String identifier, Object ogTitle, Object ogDescription, Object ogImg, Object ogType, String contentHeading, String content, String creationTime, String updateTime, String publishTime, String isActive, String featuredImg, String authorId, String pageLayout, Object layoutUpdateXml, Object customTheme, Object customLayout, Object customLayoutUpdateXml, Object customThemeFrom, Object customThemeTo, Object mediaGallery, String secret, Object viewsCount, Object isRecentPostsSkip, Object shortContent, List<String> firstStoreId, List<String> storeIds, Integer storeId, List<String> categories, Boolean ogImage, String shortFilteredContent, String filteredContent, String featuredImage, String firstImage, String postUrl) {
        super();
        this.postId = postId;
        this.title = title;
        this.metaTitle = metaTitle;
        this.metaKeywords = metaKeywords;
        this.metaDescription = metaDescription;
        this.identifier = identifier;
        this.ogTitle = ogTitle;
        this.ogDescription = ogDescription;
        this.ogImg = ogImg;
        this.ogType = ogType;
        this.contentHeading = contentHeading;
        this.content = content;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
        this.publishTime = publishTime;
        this.isActive = isActive;
        this.featuredImg = featuredImg;
        this.authorId = authorId;
        this.pageLayout = pageLayout;
        this.layoutUpdateXml = layoutUpdateXml;
        this.customTheme = customTheme;
        this.customLayout = customLayout;
        this.customLayoutUpdateXml = customLayoutUpdateXml;
        this.customThemeFrom = customThemeFrom;
        this.customThemeTo = customThemeTo;
        this.mediaGallery = mediaGallery;
        this.secret = secret;
        this.viewsCount = viewsCount;
        this.isRecentPostsSkip = isRecentPostsSkip;
        this.shortContent = shortContent;
        this.firstStoreId = firstStoreId;
        this.storeIds = storeIds;
        this.storeId = storeId;
        this.categories = categories;
        this.ogImage = ogImage;
        this.shortFilteredContent = shortFilteredContent;
        this.filteredContent = filteredContent;
        this.featuredImage = featuredImage;
        this.firstImage = firstImage;
        this.postUrl = postUrl;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(Object metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Object getOgTitle() {
        return ogTitle;
    }

    public void setOgTitle(Object ogTitle) {
        this.ogTitle = ogTitle;
    }

    public Object getOgDescription() {
        return ogDescription;
    }

    public void setOgDescription(Object ogDescription) {
        this.ogDescription = ogDescription;
    }

    public Object getOgImg() {
        return ogImg;
    }

    public void setOgImg(Object ogImg) {
        this.ogImg = ogImg;
    }

    public Object getOgType() {
        return ogType;
    }

    public void setOgType(Object ogType) {
        this.ogType = ogType;
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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getFeaturedImg() {
        return ConstantValues.ECOMMERCE_BASE_IMAGEURL+featuredImg;
    }

    public void setFeaturedImg(String featuredImg) {
        this.featuredImg = featuredImg;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getPageLayout() {
        return pageLayout;
    }

    public void setPageLayout(String pageLayout) {
        this.pageLayout = pageLayout;
    }

    public Object getLayoutUpdateXml() {
        return layoutUpdateXml;
    }

    public void setLayoutUpdateXml(Object layoutUpdateXml) {
        this.layoutUpdateXml = layoutUpdateXml;
    }

    public Object getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(Object customTheme) {
        this.customTheme = customTheme;
    }

    public Object getCustomLayout() {
        return customLayout;
    }

    public void setCustomLayout(Object customLayout) {
        this.customLayout = customLayout;
    }

    public Object getCustomLayoutUpdateXml() {
        return customLayoutUpdateXml;
    }

    public void setCustomLayoutUpdateXml(Object customLayoutUpdateXml) {
        this.customLayoutUpdateXml = customLayoutUpdateXml;
    }

    public Object getCustomThemeFrom() {
        return customThemeFrom;
    }

    public void setCustomThemeFrom(Object customThemeFrom) {
        this.customThemeFrom = customThemeFrom;
    }

    public Object getCustomThemeTo() {
        return customThemeTo;
    }

    public void setCustomThemeTo(Object customThemeTo) {
        this.customThemeTo = customThemeTo;
    }

    public Object getMediaGallery() {
        return mediaGallery;
    }

    public void setMediaGallery(Object mediaGallery) {
        this.mediaGallery = mediaGallery;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Object getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Object viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Object getIsRecentPostsSkip() {
        return isRecentPostsSkip;
    }

    public void setIsRecentPostsSkip(Object isRecentPostsSkip) {
        this.isRecentPostsSkip = isRecentPostsSkip;
    }

    public Object getShortContent() {
        return shortContent;
    }

    public void setShortContent(Object shortContent) {
        this.shortContent = shortContent;
    }

    public List<String> getFirstStoreId() {
        return firstStoreId;
    }

    public void setFirstStoreId(List<String> firstStoreId) {
        this.firstStoreId = firstStoreId;
    }

    public List<String> getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(List<String> storeIds) {
        this.storeIds = storeIds;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Boolean getOgImage() {
        return ogImage;
    }

    public void setOgImage(Boolean ogImage) {
        this.ogImage = ogImage;
    }

    public String getShortFilteredContent() {
        return shortFilteredContent;
    }

    public void setShortFilteredContent(String shortFilteredContent) {
        this.shortFilteredContent = shortFilteredContent;
    }

    public String getFilteredContent() {
        return filteredContent;
    }

    public void setFilteredContent(String filteredContent) {
        this.filteredContent = filteredContent;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(postId);
        dest.writeValue(title);
        dest.writeValue(metaTitle);
        dest.writeValue(metaKeywords);
        dest.writeValue(metaDescription);
        dest.writeValue(identifier);
        dest.writeValue(ogTitle);
        dest.writeValue(ogDescription);
        dest.writeValue(ogImg);
        dest.writeValue(ogType);
        dest.writeValue(contentHeading);
        dest.writeValue(content);
        dest.writeValue(creationTime);
        dest.writeValue(updateTime);
        dest.writeValue(publishTime);
        dest.writeValue(isActive);
        dest.writeValue(featuredImg);
        dest.writeValue(authorId);
        dest.writeValue(pageLayout);
        dest.writeValue(layoutUpdateXml);
        dest.writeValue(customTheme);
        dest.writeValue(customLayout);
        dest.writeValue(customLayoutUpdateXml);
        dest.writeValue(customThemeFrom);
        dest.writeValue(customThemeTo);
        dest.writeValue(mediaGallery);
        dest.writeValue(secret);
        dest.writeValue(viewsCount);
        dest.writeValue(isRecentPostsSkip);
        dest.writeValue(shortContent);
        dest.writeList(firstStoreId);
        dest.writeList(storeIds);
        dest.writeValue(storeId);
        dest.writeList(categories);
        dest.writeValue(ogImage);
        dest.writeValue(shortFilteredContent);
        dest.writeValue(filteredContent);
        dest.writeValue(featuredImage);
        dest.writeValue(firstImage);
        dest.writeValue(postUrl);
    }

    public int describeContents() {
        return 0;
    }

}