package com.celebritiescart.celebritiescart.models.user_model;

import java.util.List;

import com.celebritiescart.celebritiescart.models.cart_model.shipping_response.ExtensionAttributes_;
import com.celebritiescart.celebritiescart.models.product_model.CustomAttribute;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("region")
    @Expose
    private Region region;
    @SerializedName("region_id")
    @Expose
    private Integer regionId;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("street")
    @Expose
    private List<String> street = null;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("middlename")
    @Expose
    private String middlename;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    @SerializedName("vat_id")
    @Expose
    private String vatId;
    @SerializedName("default_shipping")
    @Expose
    private Boolean defaultShipping;
    @SerializedName("default_billing")
    @Expose
    private Boolean defaultBilling;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes_ extensionAttributes;
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Address() {
    }

    /**
     *
     * @param region
     * @param countryId
     * @param middlename
     * @param fax
     * @param extensionAttributes
     * @param street
     * @param defaultBilling
     * @param lastname
     * @param defaultShipping
     * @param firstname
     * @param postcode
     * @param vatId
     * @param suffix
     * @param customAttributes
     * @param city
     * @param id
     * @param customerId
     * @param prefix
     * @param company
     * @param telephone
     * @param regionId
     */
    public Address(Integer id, Integer customerId, Region region, Integer regionId, String countryId, List<String> street, String company, String telephone, String fax, String postcode, String city, String firstname, String lastname, String middlename, String prefix, String suffix, String vatId, Boolean defaultShipping, Boolean defaultBilling, ExtensionAttributes_ extensionAttributes, List<CustomAttribute> customAttributes) {
        super();
        this.id = id;
        this.customerId = customerId;
        this.region = region;
        this.regionId = regionId;
        this.countryId = countryId;
        this.street = street;
        this.company = company;
        this.telephone = telephone;
        this.fax = fax;
        this.postcode = postcode;
        this.city = city;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.prefix = prefix;
        this.suffix = suffix;
        this.vatId = vatId;
        this.defaultShipping = defaultShipping;
        this.defaultBilling = defaultBilling;
        this.extensionAttributes = extensionAttributes;
        this.customAttributes = customAttributes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public List<String> getStreet() {
        return street;
    }

    public void setStreet(List<String> street) {
        this.street = street;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    public Boolean getDefaultShipping() {
        return defaultShipping;
    }

    public void setDefaultShipping(Boolean defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    public Boolean getDefaultBilling() {
        return defaultBilling;
    }

    public void setDefaultBilling(Boolean defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public ExtensionAttributes_ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes_ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

}