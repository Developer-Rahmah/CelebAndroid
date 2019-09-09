
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtensionAttributes_ {

    @SerializedName("file_info")
    @Expose
    private FileInfo fileInfo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExtensionAttributes_() {
    }

    /**
     * 
     * @param fileInfo
     */
    public ExtensionAttributes_(FileInfo fileInfo) {
        super();
        this.fileInfo = fileInfo;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

}
