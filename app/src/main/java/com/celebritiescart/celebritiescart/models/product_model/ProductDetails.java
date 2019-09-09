package com.celebritiescart.celebritiescart.models.product_model;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.celebritiescart.celebritiescart.constant.ConstantValues.ECOMMERCE_BASE_URL;
import static com.celebritiescart.celebritiescart.constant.ConstantValues.ECOMMERCE_PRODUCT_IMAGEURL;


public class ProductDetails implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("attribute_set_id")
    @Expose
    private Double attributeSetId;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("status")
    @Expose
    private Double status;
    @SerializedName("visibility")
    @Expose
    private Double visibility;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("extension_attributes")
    @Expose
    private JSONObject extensionAttributes = null;
    @SerializedName("product_links")
    @Expose
    private List<JSONObject> productLinks = null;
    @SerializedName("tier_prices")
    @Expose
    private List<JSONObject> tierPrices = null;
    @SerializedName("custom_attributes")
    @Expose
    public List<CustomAttribute> customAttributes = new ArrayList<>();

    @SerializedName("entity_id")
    @Expose
    public String entityId;

    @SerializedName("has_options")
    @Expose
    public String hasOptions;
    @SerializedName("required_options")
    @Expose
    public String requiredOptions;


    @SerializedName("final_price")
    @Expose
    public JSONObject finalPrice;
    @SerializedName("minimal_price")
    @Expose
    public String minimalPrice;
    @SerializedName("min_price")
    @Expose
    public String minPrice;
    @SerializedName("max_price")
    @Expose
    public String maxPrice;
    @SerializedName("tier_price")
    @Expose
    public List<JSONObject> tierPrice = null;

    @SerializedName("small_image")
    @Expose
    public String smallImage;


    @SerializedName("request_path")
    @Expose
    public String requestPath;
    @SerializedName("store_id")
    @Expose
    public String storeId;

    @SerializedName("quantity_and_stock_status")
    @Expose
    public QuantityAndStockStatus quantityAndStockStatus;
    @SerializedName("sw_featured")
    @Expose
    public String swFeatured;

    @SerializedName("meta_title")
    @Expose
    public String metaTitle;
    @SerializedName("meta_description")
    @Expose
    public String metaDescription;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("page_layout")
    @Expose
    public String pageLayout;
    @SerializedName("options_container")
    @Expose
    public String optionsContainer;
    @SerializedName("url_key")
    @Expose
    public String urlKey;
    @SerializedName("swatch_image")
    @Expose
    public String swatchImage;
    @SerializedName("gift_message_available")
    @Expose
    public String giftMessageAvailable;
    @SerializedName("product_page_type")
    @Expose
    public String productPageType;
    @SerializedName("product_image_size")
    @Expose
    public String productImageSize;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("short_description")
    @Expose
    public String shortDescription;
    @SerializedName("meta_keyword")
    @Expose
    public String metaKeyword;
    @SerializedName("custom_block")
    @Expose
    public String customBlock;
    @SerializedName("options")
    @Expose
    public List<JSONObject> options = null;
    @SerializedName("media_gallery")
    @Expose
    public MediaGallery mediaGallery;

    @SerializedName("tier_price_changed")
    @Expose
    public Double tierPriceChanged;
    @SerializedName("category_ids")
    @Expose
    public List<String> categoryIds = null;
    @SerializedName("is_salable")
    @Expose
    public JSONObject isSalable;


    private String productsImage;
    private String wishlist_item_id;
    private String productsUrl;
    private int customersBasketQuantity;
    private int productsQuantity;
    private String productsPrice;
    private String attributesPrice;
    private String productsFinalPrice;
    private String totalPrice;
    private String productsDescription;
    private int categoriesId;
    private String productsWeight;
    private String productsWeightUnit;
    private String categoriesName;
    private int manufacturersId;
    private String manufacturersName;
    private int taxClassId;
    private String taxDescription;
    private String taxClassTitle;
    private String taxClassDescription;
    private String isSaleProduct;
    private int productsTaxClassId;
    private String discountPrice;
    private String isLiked = "0";
    private int likes;
    private transient ProductRating productRating;
    private List<Image> images;

    public ProductDetails(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        sku = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            attributeSetId = null;
        } else {
            attributeSetId = in.readDouble();
        }
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readDouble();
        }
        if (in.readByte() == 0) {
            visibility = null;
        } else {
            visibility = in.readDouble();
        }
        typeId = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        if (in.readByte() == 0) {
            weight = null;
        } else {
            weight = in.readDouble();
        }
        productsImage = in.readString();
        productsUrl = in.readString();
        customersBasketQuantity = in.readInt();
        productsQuantity = in.readInt();
        productsPrice = in.readString();
        attributesPrice = in.readString();
        productsFinalPrice = in.readString();
        totalPrice = in.readString();
        productsDescription = in.readString();
        categoriesId = in.readInt();
        productsWeight = in.readString();
        productsWeightUnit = in.readString();
        categoriesName = in.readString();
        manufacturersId = in.readInt();
        manufacturersName = in.readString();
        taxClassId = in.readInt();
        taxDescription = in.readString();
        taxClassTitle = in.readString();
        taxClassDescription = in.readString();
        isSaleProduct = in.readString();
        productsTaxClassId = in.readInt();
        discountPrice = in.readString();
        isLiked = in.readString();
        likes = in.readInt();
        images = in.createTypedArrayList(Image.CREATOR);
    }

    public static final Creator<ProductDetails> CREATOR = new Creator<ProductDetails>() {
        @Override
        public ProductDetails createFromParcel(Parcel in) {
            return new ProductDetails(in);
        }

        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };

    public ProductDetails() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(Double attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getStatus() {
        return status;
    }

    public void setStatus(Double status) {
        this.status = status;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

//    public JSONObject getExtensionAttributes() {
//        return extensionAttributes;
//    }
//
//    public void setExtensionAttributes(List<JSONObject> extensionAttributes) {
//        this.extensionAttributes = extensionAttributes;
//    }

    public List<JSONObject> getProductLinks() {
        return productLinks;
    }

    public void setProductLinks(List<JSONObject> productLinks) {
        this.productLinks = productLinks;
    }

    public List<JSONObject> getTierPrices() {
        return tierPrices;
    }

    public void setTierPrices(List<JSONObject> tierPrices) {
        this.tierPrices = tierPrices;
    }

    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

//    public String getProductsImage() {
//        return productsImage;
//    }

    public String getProductsImage() {
        try {
            if (customAttributes != null) {
//                productsImage = "";

                //do something

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    productsImage = ECOMMERCE_PRODUCT_IMAGEURL + customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("image")).collect(Collectors.toList()).get(0).getValue();
                } else {

                    for (CustomAttribute person : customAttributes) {
                        if (person.getAttributeCode().equals("image")) {
                            productsImage = ECOMMERCE_PRODUCT_IMAGEURL + person.getValue();
                        }
                    }

                }
                return productsImage;
            }
            return ECOMMERCE_PRODUCT_IMAGEURL + thumbnail;


        } catch (Exception e) {
            return productsImage;

        }
    }

    public String getProductsUrlKey() {
        try {
            if (customAttributes != null) {
//                productsImage = "";

                //do something

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return ECOMMERCE_BASE_URL + customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("url_key")).collect(Collectors.toList()).get(0).getValue()+".html";
                } else {

                    for (CustomAttribute person : customAttributes) {
                        if (person.getAttributeCode().equals("url_key")) {
                            return ECOMMERCE_BASE_URL + person.getValue()+".html";
                        }
                    }

                }
                return ECOMMERCE_BASE_URL;
            }
            return ECOMMERCE_BASE_URL;


        } catch (Exception e) {
            return productsImage;

        }
    }


    public String getProductsUrl() {
        return productsUrl;
    }

    public String getProductImageX() {
        return productsImage;
    }

    public int getCustomersBasketQuantity() {
        return customersBasketQuantity;
    }

    public void setProductsQuantity(int productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public String getProductsDescription() {
        if (customAttributes != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    return customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("description") || p.getAttributeCode().equals("meta_description")).collect(Collectors.toList()).get(0).getValue();
                } catch (Exception e) {
                    return "";
                }

            } else {

                for (CustomAttribute person : customAttributes) {
                    if (person.getAttributeCode().equals("description")) {
                        return person.getValue();
                    }
                }

            }
        }
        return name;
    }




    public String getVideoUrl() {
        if (customAttributes != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    return customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("video_link")).collect(Collectors.toList()).get(0).getValue();
                } catch (Exception e) {
                    return "";
                }

            } else {

                for (CustomAttribute person : customAttributes) {
                    if (person.getAttributeCode().equals("video_link")) {
                        return person.getValue();
                    }
                }

            }
        }
        return "";
    }

    public void setProductsImage(String productsImage) {
        this.productsImage = productsImage;
    }

    public void setProductsUrl(String productsUrl) {
        this.productsUrl = productsUrl;
    }

    public void setProductsPrice(String productsPrice) {
        this.productsPrice = productsPrice;
    }

    public void setCustomersBasketQuantity(int customersBasketQuantity) {
        this.customersBasketQuantity = customersBasketQuantity;
    }

    public void setAttributesPrice(String attributesPrice) {
        this.attributesPrice = attributesPrice;
    }

    public void setProductsFinalPrice(String productsFinalPrice) {
        this.productsFinalPrice = productsFinalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setProductsDescription(String productsDescription) {
        this.productsDescription = productsDescription;
    }

    public void setCategoriesId(int categoriesId) {
        this.categoriesId = categoriesId;
    }

    public void setProductsWeight(String productsWeight) {
        this.productsWeight = productsWeight;
    }

    public void setProductsWeightUnit(String productsWeightUnit) {
        this.productsWeightUnit = productsWeightUnit;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }

    public void setManufacturersId(int manufacturersId) {
        this.manufacturersId = manufacturersId;
    }

    public void setManufacturersName(String manufacturersName) {
        this.manufacturersName = manufacturersName;
    }

    public void setTaxClassId(int taxClassId) {
        this.taxClassId = taxClassId;
    }

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    public void setTaxClassTitle(String taxClassTitle) {
        this.taxClassTitle = taxClassTitle;
    }

    public void setTaxClassDescription(String taxClassDescription) {
        this.taxClassDescription = taxClassDescription;
    }

    public void setIsSaleProduct(String isSaleProduct) {
        this.isSaleProduct = isSaleProduct;
    }

    public String getProductsWeight() {
        return productsWeight;
    }

    public String getProductsWeightUnit() {
        return productsWeightUnit;
    }

    public int getProductsQuantity() {
        return productsQuantity;
    }

    public String getProductsPrice() {
        return price.toString();
    }

    public String getAttributesPrice() {
        return attributesPrice;
    }

    public String getProductsFinalPrice() {
        return productsFinalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public int getCategoriesId() {
        return categoriesId;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public int getManufacturersId() {
        return manufacturersId;
    }

    public String getManufacturersName() {
        return manufacturersName;
    }

    public int getProductsTaxClassId() {
        return productsTaxClassId;
    }

    public String getTaxDescription() {
        return taxDescription;
    }

    public String getTaxClassTitle() {
        return taxClassTitle;
    }

    public String getTaxClassDescription() {
        return taxClassDescription;
    }

    public String getIsSaleProduct() {
        return isSaleProduct;
    }

    public int getProductsId() {
        return id;
    }

    public String getProductsName() {
        return name;
    }

    public String getProductsDateAdded() {
        return createdAt;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public String getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }

    public int getTaxClassId() {
        return taxClassId;
    }

    public void setProductsTaxClassId(int productsTaxClassId) {
        this.productsTaxClassId = productsTaxClassId;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getProductsLiked() {
        return likes;
    }

    public List<Image> getImages() {
        return images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(sku);
        parcel.writeString(name);
        if (attributeSetId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(attributeSetId);
        }
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        if (status == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(status);
        }
        if (visibility == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(visibility);
        }
        parcel.writeString(typeId);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        if (weight == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(weight);
        }
        parcel.writeString(productsImage);
        parcel.writeString(productsUrl);
        parcel.writeInt(customersBasketQuantity);
        parcel.writeInt(productsQuantity);
        parcel.writeString(productsPrice);
        parcel.writeString(attributesPrice);
        parcel.writeString(productsFinalPrice);
        parcel.writeString(totalPrice);
        parcel.writeString(productsDescription);
        parcel.writeInt(categoriesId);
        parcel.writeString(productsWeight);
        parcel.writeString(productsWeightUnit);
        parcel.writeString(categoriesName);
        parcel.writeInt(manufacturersId);
        parcel.writeString(manufacturersName);
        parcel.writeInt(taxClassId);
        parcel.writeString(taxDescription);
        parcel.writeString(taxClassTitle);
        parcel.writeString(taxClassDescription);
        parcel.writeString(isSaleProduct);
        parcel.writeInt(productsTaxClassId);
        parcel.writeString(discountPrice);
        parcel.writeString(isLiked);
        parcel.writeInt(likes);
        parcel.writeTypedList(images);
    }

    public List<CustomAttribute> getAttributes() {
        return customAttributes;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getWishlist_item_id() {
        return wishlist_item_id;
    }

    public void setWishlist_item_id(String wishlist_item_id) {
        this.wishlist_item_id = wishlist_item_id;
    }

    public ProductRating getProductRating() {
        return productRating;
    }

    public void setProductRating(ProductRating productRating) {
        this.productRating = productRating;
    }

    public String getBrandsName() {
        if (customAttributes != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    return customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("brands") || p.getAttributeCode().equals("brands")).collect(Collectors.toList()).get(0).getValue();
                } catch (Exception e) {
                    return "Nil";
                }

            } else {

                for (CustomAttribute person : customAttributes) {
                    if (person.getAttributeCode().equals("brands")) {
                        return person.getValue();
                    }
                }

            }
        }
        return "Nil";
    }



}