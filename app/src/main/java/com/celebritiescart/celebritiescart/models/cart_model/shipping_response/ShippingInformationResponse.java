
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ShippingInformationResponse implements Serializable {

    @SerializedName("payment_methods")
    @Expose
    private List<PaymentMethod> paymentMethods = null;
    @SerializedName("totals")
    @Expose
    private Totals totals;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes____ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ShippingInformationResponse() {
    }

    /**
     * 
     * @param extensionAttributes
     * @param paymentMethods
     * @param totals
     */
    public ShippingInformationResponse(List<PaymentMethod> paymentMethods, Totals totals, ExtensionAttributes____ extensionAttributes) {
        super();
        this.paymentMethods = paymentMethods;
        this.totals = totals;
        this.extensionAttributes = extensionAttributes;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public Totals getTotals() {
        return totals;
    }

    public void setTotals(Totals totals) {
        this.totals = totals;
    }

    public ExtensionAttributes____ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes____ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
