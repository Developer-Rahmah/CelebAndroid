package com.celebritiescart.celebritiescart.models.category_model;

import android.os.Build;

import com.celebritiescart.celebritiescart.models.product_model.CustomAttribute;
import com.celebritiescart.celebritiescart.models.subcategory.SubCategoryModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.celebritiescart.celebritiescart.constant.ConstantValues.ECOMMERCE_CATEGORY_IMAGEURL;


public class CategoryDetailsInHome {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("is_active")
    @Expose
    private Boolean is_active;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("name")
    @Expose
    private String name;
    //    @SerializedName("order")
//    @Expose
//    private String order;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("product_count")
    @Expose
    private String totalProducts = "0";

    @SerializedName("children")
    @Expose
    public String children = "";

    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes = null;
    @SerializedName("subcategories")
    @Expose
    public List<SubCategoryModel> subCategoryModels = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getImage() {
//        return image;
//    }


    public String getImage() {

        this.image = "";
        try {
            if (customAttributes != null) {
                //do something

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    this.image = ECOMMERCE_CATEGORY_IMAGEURL + customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("custom_image")).collect(Collectors.toList()).get(0).getValue();
                } else {

                    for (CustomAttribute person : customAttributes) {
                        if (person.getAttributeCode().equals("custom_image")) {
                            this.image = ECOMMERCE_CATEGORY_IMAGEURL + person.getValue();
                        }
                    }

                }
            }
            return this.image;

        } catch (Exception e) {
            return this.image;

        }
    }


    public String getProductsThumbnail() {
        if (customAttributes != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                try {
//                    return ECOMMERCE_CATEGORY_IMAGEURL + customAttributes.stream().filter(Objects::nonNull)
//                            .filter(p -> p.getAttributeCode().equals("thumbnail") || p.getAttributeCode().equals("image")).collect(Collectors.toList()).get(0).getValue();
//                } catch (Exception e) {
//                    return "yoo";
//                }
//
//            } else {

            for (CustomAttribute person : customAttributes) {
                if (person.getAttributeCode().equals("thumbnail")) {
                    return ECOMMERCE_CATEGORY_IMAGEURL + person.getValue();
                }

            }
            for (CustomAttribute person : customAttributes) {
                if (person.getAttributeCode().equals("custom_image")) {
                    return ECOMMERCE_CATEGORY_IMAGEURL + person.getValue();
                }

            }

//            }
        }
        return "yoo";
    }

    public String getSlug() {

        String slug = "";
        try {
            if (customAttributes != null) {
                //do something

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    slug = customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("url_key")).collect(Collectors.toList()).get(0).getValue();
                } else {

                    for (CustomAttribute person : customAttributes) {
                        if (person.getAttributeCode().equals("url_key")) {
                            slug = person.getValue();
                        }
                    }

                }
            }
            return slug;

        } catch (Exception e) {
            return slug;

        }
    }


    public void setImage(String image) {
        this.image = image;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }





    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }






    public void setName(String name) {
        this.name = name;
    }

//    public String getOrder() {
//        return order;
//    }
//
//    public void setOrder(String order) {
//        this.order = order;
//    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(String totalProducts) {
        this.totalProducts = totalProducts;
    }

    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public String getFacebookURL() {
        String slug = "";
        try {
            if (customAttributes != null) {
                //do something

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    slug = customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("facebook")).collect(Collectors.toList()).get(0).getValue();
                } else {

                    for (CustomAttribute person : customAttributes) {
                        if (person.getAttributeCode().equals("facebook")) {
                            slug = person.getValue();
                        }
                    }

                }
            }
            return slug;

        } catch (Exception e) {
            return slug;

        }
    }

    public String getTwitterURL() {
        String slug = "";
        try {
            if (customAttributes != null) {
                //do something

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    slug = customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("twitter")).collect(Collectors.toList()).get(0).getValue();
                } else {

                    for (CustomAttribute person : customAttributes) {
                        if (person.getAttributeCode().equals("twitter")) {
                            slug = person.getValue();
                        }
                    }

                }
            }
            return slug;

        } catch (Exception e) {
            return slug;

        }
    }

    public String getInstagramURL() {
        String slug = "";
        try {
            if (customAttributes != null) {
                //do something

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    slug = customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("instagram")).collect(Collectors.toList()).get(0).getValue();
                } else {

                    for (CustomAttribute person : customAttributes) {
                        if (person.getAttributeCode().equals("instagram")) {
                            slug = person.getValue();
                        }
                    }

                }
            }
            return slug;

        } catch (Exception e) {
            return slug;

        }
    }

    public boolean isCelebrity(){
        return getParentId().equals("48");
    }
    public boolean isBrand(){
        return getParentId().equals("29");
    }

    public String getSnapchatURL() {
        String slug = "";
        try {
            if (customAttributes != null) {
                //do something

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    slug = customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("snapchat")).collect(Collectors.toList()).get(0).getValue();
                } else {

                    for (CustomAttribute person : customAttributes) {
                        if (person.getAttributeCode().equals("snapchat")) {
                            slug = person.getValue();
                        }
                    }

                }
            }
            return slug;

        } catch (Exception e) {
            return slug;

        }
    }
}
