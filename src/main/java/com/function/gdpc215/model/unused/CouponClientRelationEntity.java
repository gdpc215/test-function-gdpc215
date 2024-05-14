package com.function.gdpc215.model.unused;

import java.util.Date;

import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class CouponClientRelationEntity {

    public String couponId;
    public String clientId;
    public Date dateCreation;

    public CouponClientRelationEntity() {
        this.couponId = "";
        this.clientId = "";
        this.dateCreation = new Date();
    }

    public CouponClientRelationEntity(JSONObject jsonObject) {
        this.couponId = jsonObject.optString("couponId");
        this.clientId = jsonObject.optString("clientId");
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
    }

    public CouponClientRelationEntity(String couponId, String clientId, Date dateCreation) {
        this.couponId = couponId;
        this.clientId = clientId;
        this.dateCreation = dateCreation;
    }
}
