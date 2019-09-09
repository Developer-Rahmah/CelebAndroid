package com.celebritiescart.celebritiescart.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("template")
    @Expose
    private String template = "email_reset";
    @SerializedName("websiteId")
    @Expose
    private Integer websiteId = 1;

    /**
     * No args constructor for use in serialization
     *
     */
    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String email) {
        this.email = email;
        this.template =  "email_reset";
        this.websiteId = 1;
    }

    /**
     *
     * @param template
     * @param websiteId
     * @param email
     */
    public ForgotPasswordRequest(String email, String template, Integer websiteId) {
        super();
        this.email = email;
        this.template = template;
        this.websiteId = websiteId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

}
