
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExtensionAttributes implements Serializable {

    @SerializedName("negotiable_quote_item_totals")
    @Expose
    private NegotiableQuoteItemTotals negotiableQuoteItemTotals;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExtensionAttributes() {
    }

    /**
     * 
     * @param negotiableQuoteItemTotals
     */
    public ExtensionAttributes(NegotiableQuoteItemTotals negotiableQuoteItemTotals) {
        super();
        this.negotiableQuoteItemTotals = negotiableQuoteItemTotals;
    }

    public NegotiableQuoteItemTotals getNegotiableQuoteItemTotals() {
        return negotiableQuoteItemTotals;
    }

    public void setNegotiableQuoteItemTotals(NegotiableQuoteItemTotals negotiableQuoteItemTotals) {
        this.negotiableQuoteItemTotals = negotiableQuoteItemTotals;
    }

}
