package com.function.gdpc215.model.unused;

import java.util.Date;

import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class AvailabilityProductRelationEntity {

    public String availabilityId;
    public String productId;
    public Date dateCreation;

    public AvailabilityProductRelationEntity() {
        this.availabilityId = "";
        this.productId = "";
        this.dateCreation = new Date();
    }

    public AvailabilityProductRelationEntity(JSONObject jsonObject) {
        this.availabilityId = jsonObject.optString("availabilityId");
        this.productId = jsonObject.optString("productId");
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
    }

    public AvailabilityProductRelationEntity(String availabilityId, String productId, Date dateCreation) {
        this.availabilityId = availabilityId;
        this.productId = productId;
        this.dateCreation = dateCreation;
    }
}
