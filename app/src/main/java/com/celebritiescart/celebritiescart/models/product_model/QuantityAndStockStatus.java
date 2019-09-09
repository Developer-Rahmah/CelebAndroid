package com.celebritiescart.celebritiescart.models.product_model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuantityAndStockStatus {

    @SerializedName("is_in_stock")
    @Expose
    public Boolean isInStock;
    @SerializedName("qty")
    @Expose
    public Integer qty;

    public QuantityAndStockStatus withIsInStock(Boolean isInStock) {
        this.isInStock = isInStock;
        return this;
    }

    public QuantityAndStockStatus withQty(Integer qty) {
        this.qty = qty;
        return this;
    }

}