
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExtensionAttributes {

    @SerializedName("custom_options")
    @Expose
    private List<CustomOption> customOptions = null;
    @SerializedName("bundle_options")
    @Expose
    private List<BundleOption> bundleOptions = null;
    @SerializedName("configurable_item_options")
    @Expose
    private List<ConfigurableItemOption> configurableItemOptions = null;
    @SerializedName("downloadable_option")
    @Expose
    private DownloadableOption downloadableOption;
    @SerializedName("giftcard_item_option")
    @Expose
    private GiftcardItemOption giftcardItemOption;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExtensionAttributes() {
    }

    /**
     * 
     * @param downloadableOption
     * @param customOptions
     * @param bundleOptions
     * @param giftcardItemOption
     * @param configurableItemOptions
     */
    public ExtensionAttributes(List<CustomOption> customOptions, List<BundleOption> bundleOptions, List<ConfigurableItemOption> configurableItemOptions, DownloadableOption downloadableOption, GiftcardItemOption giftcardItemOption) {
        super();
        this.customOptions = customOptions;
        this.bundleOptions = bundleOptions;
        this.configurableItemOptions = configurableItemOptions;
        this.downloadableOption = downloadableOption;
        this.giftcardItemOption = giftcardItemOption;
    }

    public List<CustomOption> getCustomOptions() {
        return customOptions;
    }

    public void setCustomOptions(List<CustomOption> customOptions) {
        this.customOptions = customOptions;
    }

    public List<BundleOption> getBundleOptions() {
        return bundleOptions;
    }

    public void setBundleOptions(List<BundleOption> bundleOptions) {
        this.bundleOptions = bundleOptions;
    }

    public List<ConfigurableItemOption> getConfigurableItemOptions() {
        return configurableItemOptions;
    }

    public void setConfigurableItemOptions(List<ConfigurableItemOption> configurableItemOptions) {
        this.configurableItemOptions = configurableItemOptions;
    }

    public DownloadableOption getDownloadableOption() {
        return downloadableOption;
    }

    public void setDownloadableOption(DownloadableOption downloadableOption) {
        this.downloadableOption = downloadableOption;
    }

    public GiftcardItemOption getGiftcardItemOption() {
        return giftcardItemOption;
    }

    public void setGiftcardItemOption(GiftcardItemOption giftcardItemOption) {
        this.giftcardItemOption = giftcardItemOption;
    }

}
