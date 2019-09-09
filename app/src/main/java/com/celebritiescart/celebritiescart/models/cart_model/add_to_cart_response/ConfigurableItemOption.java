
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigurableItemOption {

    @SerializedName("option_id")
    @Expose
    private String optionId;
    @SerializedName("option_value")
    @Expose
    private Integer optionValue;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes___ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ConfigurableItemOption() {
    }

    /**
     * 
     * @param optionId
     * @param extensionAttributes
     * @param optionValue
     */
    public ConfigurableItemOption(String optionId, Integer optionValue, ExtensionAttributes___ extensionAttributes) {
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

    public Integer getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(Integer optionValue) {
        this.optionValue = optionValue;
    }

    public ExtensionAttributes___ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes___ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
