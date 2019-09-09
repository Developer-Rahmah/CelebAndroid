package com.celebritiescart.celebritiescart.models.cart_model;

import com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response.AddressInformation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressInformationMainBody {
    @SerializedName("addressInformation")
    @Expose
    private AddressInformation addressInformation;

    public AddressInformationMainBody(AddressInformation addressInformation) {
        this.addressInformation = addressInformation;
    }

    public AddressInformation getAddressInformation() {
        return addressInformation;
    }

    public void setAddressInformation(AddressInformation addressInformation) {
        this.addressInformation = addressInformation;
    }
}
