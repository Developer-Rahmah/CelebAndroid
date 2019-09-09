
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtensionAttributes_____ {

    @SerializedName("negotiable_quote_item")
    @Expose
    private NegotiableQuoteItem negotiableQuoteItem;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExtensionAttributes_____() {
    }

    /**
     * 
     * @param negotiableQuoteItem
     */
    public ExtensionAttributes_____(NegotiableQuoteItem negotiableQuoteItem) {
        super();
        this.negotiableQuoteItem = negotiableQuoteItem;
    }

    public NegotiableQuoteItem getNegotiableQuoteItem() {
        return negotiableQuoteItem;
    }

    public void setNegotiableQuoteItem(NegotiableQuoteItem negotiableQuoteItem) {
        this.negotiableQuoteItem = negotiableQuoteItem;
    }

}
