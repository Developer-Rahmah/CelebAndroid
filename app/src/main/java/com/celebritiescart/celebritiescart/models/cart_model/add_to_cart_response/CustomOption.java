
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomOption {

    @SerializedName("option_id")
    @Expose
    private String optionId;
    @SerializedName("option_value")
    @Expose
    private String optionValue;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes_ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CustomOption() {
    }

    /**
     * 
     * @param optionId
     * @param extensionAttributes
     * @param optionValue
     */
    public CustomOption(String optionId, String optionValue, ExtensionAttributes_ extensionAttributes) {
        super();
        this.optionId = optionId;
        this.optionValue = optionValue;
        this.extensionAttributes = extensionAttributes;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public ExtensionAttributes_ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes_ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
