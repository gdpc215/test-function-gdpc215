package com.function.gdpc215.model.unused;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class DiscountProductRelationEntity implements Serializable {

    public String discountId;
    public String productId;
    public Date dateCreation;

    public DiscountProductRelationEntity() {
        this.discountId = "";
        this.productId = "";
        this.dateCreation = new Date();
    }

    public DiscountProductRelationEntity(JSONObject jsonObject) {
        this.discountId = jsonObject.optString("discountId");
        this.productId = jsonObject.optString("productId");
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
    }

    public DiscountProductRelationEntity(String discountId, String productId, Date dateCreation) {
        this.discountId = discountId;
        this.productId = productId;
        this.dateCreation = dateCreation;
    }
}
