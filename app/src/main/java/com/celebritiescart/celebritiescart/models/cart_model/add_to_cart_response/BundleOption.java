
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BundleOption {

    @SerializedName("option_id")
    @Expose
    private Integer optionId;
    @SerializedName("option_qty")
    @Expose
    private Integer optionQty;
    @SerializedName("option_selections")
    @Expose
    private List<Integer> optionSelections = null;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes__ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BundleOption() {
    }

    /**
     * 
     * @param optionId
     * @param optionSelections
     * @param extensionAttributes
     * @param optionQty
     */
    public BundleOption(Integer optionId, Integer optionQty, List<Integer> optionSelections, ExtensionAttributes__ extensionAttributes) {
        super();
        this.optionId = optionId;
        this.optionQty = optionQty;
        this.optionSelections = optionSelections;
        this.extensionAttributes = extensionAttributes;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getOptionQty() {
        return optionQty;
    }

    public void setOptionQty(Integer optionQty) {
        this.optionQty = optionQty;
    }

    public List<Integer> getOptionSelections() {
        return optionSelections;
    }

    public void setOptionSelections(List<Integer> optionSelections) {
        this.optionSelections = optionSelections;
    }

    public ExtensionAttributes__ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes__ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
