
package com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GiftcardItemOption {

    @SerializedName("giftcard_amount")
    @Expose
    private String giftcardAmount;
    @SerializedName("custom_giftcard_amount")
    @Expose
    private Integer customGiftcardAmount;
    @SerializedName("giftcard_sender_name")
    @Expose
    private String giftcardSenderName;
    @SerializedName("giftcard_recipient_name")
    @Expose
    private String giftcardRecipientName;
    @SerializedName("giftcard_sender_email")
    @Expose
    private String giftcardSenderEmail;
    @SerializedName("giftcard_recipient_email")
    @Expose
    private String giftcardRecipientEmail;
    @SerializedName("giftcard_message")
    @Expose
    private String giftcardMessage;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes____ extensionAttributes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GiftcardItemOption() {
    }

    /**
     * 
     * @param giftcardSenderEmail
     * @param giftcardMessage
     * @param giftcardRecipientEmail
     * @param extensionAttributes
     * @param customGiftcardAmount
     * @param giftcardSenderName
     * @param giftcardAmount
     * @param giftcardRecipientName
     */
    public GiftcardItemOption(String giftcardAmount, Integer customGiftcardAmount, String giftcardSenderName, String giftcardRecipientName, String giftcardSenderEmail, String giftcardRecipientEmail, String giftcardMessage, ExtensionAttributes____ extensionAttributes) {
        super();
        this.giftcardAmount = giftcardAmount;
        this.customGiftcardAmount = customGiftcardAmount;
        this.giftcardSenderName = giftcardSenderName;
        this.giftcardRecipientName = giftcardRecipientName;
        this.giftcardSenderEmail = giftcardSenderEmail;
        this.giftcardRecipientEmail = giftcardRecipientEmail;
        this.giftcardMessage = giftcardMessage;
        this.extensionAttributes = extensionAttributes;
    }

    public String getGiftcardAmount() {
        return giftcardAmount;
    }

    public void setGiftcardAmount(String giftcardAmount) {
        this.giftcardAmount = giftcardAmount;
    }

    public Integer getCustomGiftcardAmount() {
        return customGiftcardAmount;
    }

    public void setCustomGiftcardAmount(Integer customGiftcardAmount) {
        this.customGiftcardAmount = customGiftcardAmount;
    }

    public String getGiftcardSenderName() {
        return giftcardSenderName;
    }

    public void setGiftcardSenderName(String giftcardSenderName) {
        this.giftcardSenderName = giftcardSenderName;
    }

    public String getGiftcardRecipientName() {
        return giftcardRecipientName;
    }

    public void setGiftcardRecipientName(String giftcardRecipientName) {
        this.giftcardRecipientName = giftcardRecipientName;
    }

    public String getGiftcardSenderEmail() {
        return giftcardSenderEmail;
    }

    public void setGiftcardSenderEmail(String giftcardSenderEmail) {
        this.giftcardSenderEmail = giftcardSenderEmail;
    }

    public String getGiftcardRecipientEmail() {
        return giftcardRecipientEmail;
    }

    public void setGiftcardRecipientEmail(String giftcardRecipientEmail) {
        this.giftcardRecipientEmail = giftcardRecipientEmail;
    }

    public String getGiftcardMessage() {
        return giftcardMessage;
    }

    public void setGiftcardMessage(String giftcardMessage) {
        this.giftcardMessage = giftcardMessage;
    }

    public ExtensionAttributes____ getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes____ extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

}
