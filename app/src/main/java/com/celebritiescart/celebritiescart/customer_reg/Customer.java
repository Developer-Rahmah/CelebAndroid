package com.celebritiescart.celebritiescart.customer_reg;


import com.celebritiescart.celebritiescart.models.product_model.CustomAttribute;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes = null;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Customer() {
    }

    /**
     *
     * @param email
     * @param lastname
     * @param firstname
     * @param telephone
     */
    public Customer(String email, String firstname, String lastname, String telephone) {
        super();
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.customAttributes = new ArrayList<>();
       // this.customAttributes.add(new CustomAttribute("telephone",telephone));
        this.customAttributes.add(new CustomAttribute("mobile_no",telephone));

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getTelephone() {
        if(this.customAttributes!=null) {
            if(this.customAttributes.get(0).getAttributeCode().equals("telephone")) {
                return this.customAttributes.get(0).getValue();
            }
            else {
                return "";
            }
        } else {
            return "";

        }
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }
}
