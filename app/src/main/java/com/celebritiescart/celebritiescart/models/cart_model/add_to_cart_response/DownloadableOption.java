
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadableOption {

    @SerializedName("downloadable_links")
    @Expose
    private List<Integer> downloadableLinks = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DownloadableOption() {
    }

    /**
     * 
     * @param downloadableLinks
     */
    public DownloadableOption(List<Integer> downloadableLinks) {
        super();
        this.downloadableLinks = downloadableLinks;
    }

    public List<Integer> getDownloadableLinks() {
        return downloadableLinks;
    }

    public void setDownloadableLinks(List<Integer> downloadableLinks) {
        this.downloadableLinks = downloadableLinks;
    }

}
