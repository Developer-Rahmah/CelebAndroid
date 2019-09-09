package com.celebritiescart.celebritiescart.models.user_model;


import com.celebritiescart.celebritiescart.models.cart_model.shipping_response.ExtensionAttributes___;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyAttributes {

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes___ extensionAttributes;

    /**
     * No args constructor for use in serialization
     *
     */
    public CompanyAttributes() {
    }

    /**
     *
     * @param customerId
     * @param status
     * @param extensionAttributes
     * @param companyId
     * @param telephone
     * @param jobTitle
     */
    public CompanyAttributes(Integer customerId, Integer companyId, String jobTitle, Integer status, String telephone, ExtensionAttributes___ extensionAttributes) {
        super();
        this.customerId = customerId;
        this.companyId = companyId;
        this.jobTitle = jobTitle;
        this.status = status;
        this.telephone = telephone;
        this.extensionAttributes = extensionAttributes;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public ExtensionAttributes___ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes___ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}