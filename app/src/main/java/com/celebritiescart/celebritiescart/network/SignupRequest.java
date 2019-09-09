package com.celebritiescart.celebritiescart.network;
import com.celebritiescart.celebritiescart.customer_reg.Customer;
import com.celebritiescart.celebritiescart.customer_reg.ExtensionAttributes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SignupRequest {
    public SignupRequest() {
    }



    public SignupRequest(String email, String firstname, String lastname, String telephone, String password) {
        super();
        this.customer = new Customer(email,firstname,lastname,telephone);
        this.password = password;
    }
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("password")
    @Expose
    private String password;


    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes = null;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }
}
