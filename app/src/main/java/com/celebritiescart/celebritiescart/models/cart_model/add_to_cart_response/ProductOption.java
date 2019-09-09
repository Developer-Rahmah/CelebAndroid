
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOption {

    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductOption() {
    }

    /**
     * 
     * @param extensionAttributes
     */
    public ProductOption(ExtensionAttributes extensionAttributes) {
        super();
        this.extensionAttributes = extensionAttributes;
    }

    public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
