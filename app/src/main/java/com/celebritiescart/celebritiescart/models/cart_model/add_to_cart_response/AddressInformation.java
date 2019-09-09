package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;



import com.celebritiescart.celebritiescart.models.address_model.AddressDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressInformation {

    @SerializedName("shippingAddress")
    @Expose
    private AddressDetails shippingAddress;
    @SerializedName("billingAddress")
    @Expose
    private AddressDetails billingAddress;
    @SerializedName("shipping_method_code")
    @Expose
    private String shippingMethodCode = "flatrate";
    @SerializedName("shipping_carrier_code")
    @Expose
    private String shippingCarrierCode = "flatrate";

    /**
     * No args constructor for use in serialization
     *
     */
    public AddressInformation() {
    }

    /**
     *
     * @param shippingCarrierCode
     * @param billingAddress
     * @param shippingMethodCode
     * @param shippingAddress
     */
    public AddressInformation(AddressDetails shippingAddress, AddressDetails billingAddress, String shippingMethodCode, String shippingCarrierCode) {
        super();
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.shippingMethodCode = shippingMethodCode;
        this.shippingCarrierCode = shippingCarrierCode;
    }

    public AddressDetails getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDetails shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressDetails getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDetails billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingMethodCode() {
        return shippingMethodCode;
    }

    public void setShippingMethodCode(String shippingMethodCode) {
        this.shippingMethodCode = shippingMethodCode;
    }

    public String getShippingCarrierCode() {
        return shippingCarrierCode;
    }

    public void setShippingCarrierCode(String shippingCarrierCode) {
        this.shippingCarrierCode = shippingCarrierCode;
    }

}