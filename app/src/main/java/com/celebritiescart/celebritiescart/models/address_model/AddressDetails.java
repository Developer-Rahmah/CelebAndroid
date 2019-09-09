package com.celebritiescart.celebritiescart.models.address_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class AddressDetails {


    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname = "\u200D";
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("street")
    @Expose
    private List<String> street = new ArrayList<>();
    @SerializedName("prefix")
    @Expose
    private String suburb = "address_";
    @SerializedName("postcode")
    @Expose
    private String postcode = "11110";
    @SerializedName("city")
    @Expose
    private String city;
    //    @SerializedName("state")
//    @Expose
//    private String state;
    @SerializedName("countryId")
    @Expose
    private String countriesId;
    @SerializedName("regionId")
    @Expose
    private String zoneId;
    @SerializedName("regionCode")
    @Expose
    private String zoneCode;
    @SerializedName("region")
    @Expose
    private String region;
    //    @SerializedName("sameAsBilling")
//    @Expose
    private transient Integer defaultAddress;


    private transient String countryName;
    private transient String zoneName;
    private transient Integer addressId;

    /**
     *
     * @return
     *     The addressId
     */

    /**
     *
     * @param addressId
     *     The address_id
     */
//    public void setAddressId(int addressId) {
////        this.addressId = addressId;
//    }

    /**
     *
     * @return
     *     The gender
     */
//    public String getGender() {
////        return gender;
//
//    }

    /**
     *
     * @param gender
     *     The gender
     */
//    public void setGender(String gender) {
////        this.gender = gender;
//    }

    /**
     * @return The company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return The lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname The lastname
     */
    public void setLastname(String lastname) {

        if (lastname != null && !lastname.equals("")) {
            this.lastname = lastname;
        } else {
            this.lastname = "\u200D";
        }
    }

    /**
     * @return The street
     */
    public List<String> getStreet() {
        return street;
    }

    /**
     * @param street The street
     */
    public void setStreet(String street) {
        this.street.add(street);
    }

    /**
     * @return The suburb
     */
    public String getSuburb() {
        return suburb;
    }

    /**
     * @param suburb The suburb
     */
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    /**
     * @return The postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode The postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = "11110";
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     *     The state
     */
//    public String getState() {
////        return state;
//    }

    /**
     *
     * @param state
     *     The state
     */
//    public void setState(String state) {
////        this.state = state;
//    }

    /**
     * @return The countriesId
     */
    public String getCountriesId() {
        return countriesId;
    }

    /**
     * @param countriesId The countries_id
     */
    public void setCountriesId(String countriesId) {
        this.countriesId = countriesId;
    }

    /**
     * @return The countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName The country_name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     *
     * @return
     *     The zoneId
     */
//    public int getZoneId() {
////        return zoneId;
//    }

    /**
     *
     * @param zoneId
     *     The zone_id
     */
//    public void setZoneId(int zoneId) {
////        this.zoneId = zoneId;
//    }

    /**
     * @return The zoneCode
     */
    public String getZoneCode() {
        return zoneCode;
    }

    /**
     * @param zoneCode The zone_code
     */
    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    /**
     * @return The zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * @param zoneName The zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * @return The defaultAddress
     */
    public int getDefaultAddress() {
        return defaultAddress;
    }

    /**
     * @param defaultAddress The default_address
     */
    public void setDefaultAddress(int defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
