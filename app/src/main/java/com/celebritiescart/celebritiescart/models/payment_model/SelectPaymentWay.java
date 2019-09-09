package com.celebritiescart.celebritiescart.models.payment_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectPaymentWay {

    @SerializedName("paymentMethod")
    @Expose
    private PaymentMethod paymentMethod;

   /* @SerializedName("transactionId")
    @Expose
    private String transactionId="";*/


    /**
     * No args constructor for use in serialization
     *
     */
    public SelectPaymentWay() {
    }

    /**
     *
     * @param paymentMethod
     */
    public SelectPaymentWay(PaymentMethod paymentMethod) {
        super();
        this.paymentMethod = paymentMethod;
       // this.transactionId=transactionId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}