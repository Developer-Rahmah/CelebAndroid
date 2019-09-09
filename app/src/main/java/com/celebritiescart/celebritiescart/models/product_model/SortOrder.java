
package com.celebritiescart.celebritiescart.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SortOrder {

    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("direction")
    @Expose
    private String direction;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
