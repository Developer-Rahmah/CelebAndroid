
package com.celebritiescart.celebritiescart.models.cart_model.shipping_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExtensionAttributes___ implements Serializable {

    @SerializedName("coupon_label")
    @Expose
    private String couponLabel;
    @SerializedName("base_customer_balance_amount")
    @Expose
    private Integer baseCustomerBalanceAmount;
    @SerializedName("customer_balance_amount")
    @Expose
    private Integer customerBalanceAmount;
    @SerializedName("negotiable_quote_totals")
    @Expose
    private NegotiableQuoteTotals negotiableQuoteTotals;
    @SerializedName("reward_points_balance")
    @Expose
    private Integer rewardPointsBalance;
    @SerializedName("reward_currency_amount")
    @Expose
    private Integer rewardCurrencyAmount;
    @SerializedName("base_reward_currency_amount")
    @Expose
    private Integer baseRewardCurrencyAmount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExtensionAttributes___() {
    }

    /**
     * 
     * @param customerBalanceAmount
     * @param rewardCurrencyAmount
     * @param negotiableQuoteTotals
     * @param couponLabel
     * @param baseCustomerBalanceAmount
     * @param rewardPointsBalance
     * @param baseRewardCurrencyAmount
     */
    public ExtensionAttributes___(String couponLabel, Integer baseCustomerBalanceAmount, Integer customerBalanceAmount, NegotiableQuoteTotals negotiableQuoteTotals, Integer rewardPointsBalance, Integer rewardCurrencyAmount, Integer baseRewardCurrencyAmount) {
        super();
        this.couponLabel = couponLabel;
        this.baseCustomerBalanceAmount = baseCustomerBalanceAmount;
        this.customerBalanceAmount = customerBalanceAmount;
        this.negotiableQuoteTotals = negotiableQuoteTotals;
        this.rewardPointsBalance = rewardPointsBalance;
        this.rewardCurrencyAmount = rewardCurrencyAmount;
        this.baseRewardCurrencyAmount = baseRewardCurrencyAmount;
    }

    public String getCouponLabel() {
        return couponLabel;
    }

    public void setCouponLabel(String couponLabel) {
        this.couponLabel = couponLabel;
    }

    public Integer getBaseCustomerBalanceAmount() {
        return baseCustomerBalanceAmount;
    }

    public void setBaseCustomerBalanceAmount(Integer baseCustomerBalanceAmount) {
        this.baseCustomerBalanceAmount = baseCustomerBalanceAmount;
    }

    public Integer getCustomerBalanceAmount() {
        return customerBalanceAmount;
    }

    public void setCustomerBalanceAmount(Integer customerBalanceAmount) {
        this.customerBalanceAmount = customerBalanceAmount;
    }

    public NegotiableQuoteTotals getNegotiableQuoteTotals() {
        return negotiableQuoteTotals;
    }

    public void setNegotiableQuoteTotals(NegotiableQuoteTotals negotiableQuoteTotals) {
        this.negotiableQuoteTotals = negotiableQuoteTotals;
    }

    public Integer getRewardPointsBalance() {
        return rewardPointsBalance;
    }

    public void setRewardPointsBalance(Integer rewardPointsBalance) {
        this.rewardPointsBalance = rewardPointsBalance;
    }

    public Integer getRewardCurrencyAmount() {
        return rewardCurrencyAmount;
    }

    public void setRewardCurrencyAmount(Integer rewardCurrencyAmount) {
        this.rewardCurrencyAmount = rewardCurrencyAmount;
    }

    public Integer getBaseRewardCurrencyAmount() {
        return baseRewardCurrencyAmount;
    }

    public void setBaseRewardCurrencyAmount(Integer baseRewardCurrencyAmount) {
        this.baseRewardCurrencyAmount = baseRewardCurrencyAmount;
    }

}
