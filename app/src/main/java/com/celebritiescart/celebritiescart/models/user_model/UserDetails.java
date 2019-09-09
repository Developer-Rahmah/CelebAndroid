package com.celebritiescart.celebritiescart.models.user_model;

import android.os.Build;

import com.celebritiescart.celebritiescart.models.cart_model.shipping_response.ExtensionAttributes__;
import com.celebritiescart.celebritiescart.models.product_model.CustomAttribute;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class UserDetails {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("default_billing")
    @Expose
    private String defaultBilling;
    @SerializedName("default_shipping")
    @Expose
    private String defaultShipping;
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_in")
    @Expose
    private String createdIn;

    @Expose
    private String customersMiddlename;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;

    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("taxvat")
    @Expose
    private String taxvat;
    @SerializedName("website_id")
    @Expose
    private Integer websiteId;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("disable_auto_group_change")
    @Expose
    private Integer disableAutoGroupChange;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes__ extensionAttributes;
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes = null;

    @SerializedName("customers_id")
    @Expose
    private String customersId;
    @SerializedName("firstname")
    @Expose
    private String customersFirstname;
    @SerializedName("lastname")
    @Expose
    private String customersLastname;
    @SerializedName("dob")
    @Expose
    private String customersDob;
    @SerializedName("gender")
    @Expose
    private String customersGender;
    @SerializedName("customers_picture")
    @Expose
    private String customersPicture;
    @SerializedName("email")
    @Expose
    private String customersEmailAddress;
    @SerializedName("customers_password")
    @Expose
    private String customersPassword;
    @SerializedName("customers_telephone")
    @Expose
    private String customersTelephone;
    @SerializedName("customers_fax")
    @Expose
    private String customersFax;
    @SerializedName("customers_newsletter")
    @Expose
    private String customersNewsletter;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("isActive")
    @Expose
    private int isActive;
    @SerializedName("customers_default_address_id")
    @Expose
    private String customersDefaultAddressId;
    @SerializedName("liked_products")
    @Expose
    private List<UserLikedProducts> likedProducts = null;
    private transient String mainId;


    public String getCustomersId() {
        return customersId;
    }

    public void setCustomersId(String customersId) {
        this.customersId = customersId;
    }

    public String getCustomersGender() {
        return customersGender;
    }

    public void setCustomersGender(String customersGender) {
        this.customersGender = customersGender;
    }

    public String getCustomersFirstname() {
        return customersFirstname;
    }

    public void setCustomersFirstname(String customersFirstname) {
        this.customersFirstname = customersFirstname;
    }

    public String getCustomersLastname() {
        return customersLastname;
    }

    public void setCustomersLastname(String customersLastname) {
        this.customersLastname = customersLastname;
    }

    public String getCustomersDob() {
        return customersDob;
    }

    public void setCustomersDob(String customersDob) {
        this.customersDob = customersDob;
    }

    public String getCustomersEmailAddress() {
        return customersEmailAddress;
    }

    public void setCustomersEmailAddress(String customersEmailAddress) {
        this.customersEmailAddress = customersEmailAddress;
    }

    public String getCustomersDefaultAddressId() {
        return customersDefaultAddressId;
    }

    public void setCustomersDefaultAddressId(String customersDefaultAddressId) {
        this.customersDefaultAddressId = customersDefaultAddressId;
    }

    public String getCustomersTelephone() {
        return customersTelephone;
    }

    public void setCustomersTelephone(String customersTelephone) {
        this.customersTelephone = customersTelephone;
    }

    public String getCustomersFax() {
        return customersFax;
    }

    public void setCustomersFax(String customersFax) {
        this.customersFax = customersFax;
    }

    public String getCustomersPassword() {
        return customersPassword;
    }

    public void setCustomersPassword(String customersPassword) {
        this.customersPassword = customersPassword;
    }

    public String getCustomersNewsletter() {
        return customersNewsletter;
    }

    public void setCustomersNewsletter(String customersNewsletter) {
        this.customersNewsletter = customersNewsletter;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getCustomersPicture() {
        try {
            if (customAttributes != null) {
//                productsImage = "";

                //do something

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    customersPicture = customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("profile_image")).collect(Collectors.toList()).get(0).getValue();
                } else {

                    for (CustomAttribute person : customAttributes) {
                        if (person.getAttributeCode().equals("profile_image")) {
                            customersPicture = person.getValue();
                        }
                    }

                }
                return customersPicture;
            }
            return "";


        } catch (Exception e) {
            return "";

        }
    }

    public void setCustomersPicture(String customersPicture) {
        this.customersPicture = customersPicture;
    }

    public List<UserLikedProducts> getLikedProducts() {
        return likedProducts;
    }

    public void setLikedProducts(List<UserLikedProducts> likedProducts) {
        this.likedProducts = likedProducts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public ExtensionAttributes__ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes__ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Integer getDisableAutoGroupChange() {
        return disableAutoGroupChange;
    }

    public void setDisableAutoGroupChange(Integer disableAutoGroupChange) {
        this.disableAutoGroupChange = disableAutoGroupChange;
    }

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    public String getTaxvat() {
        return taxvat;
    }

    public void setTaxvat(String taxvat) {
        this.taxvat = taxvat;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCustomersMiddlename() {
        return customersMiddlename;
    }

    public void setCustomersMiddlename(String customersMiddlename) {
        this.customersMiddlename = customersMiddlename;
    }

    public String getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(String createdIn) {
        this.createdIn = createdIn;
    }

    public String getDefaultBilling() {
        return defaultBilling;
    }

    public void setDefaultBilling(String defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public String getDefaultShipping() {
        return defaultShipping;
    }

    public void setDefaultShipping(String defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    public void setId(String id) {
        this.mainId = id;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public Integer getId() {
        return id;
    }
}

