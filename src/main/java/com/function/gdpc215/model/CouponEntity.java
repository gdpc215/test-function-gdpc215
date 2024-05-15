package com.function.gdpc215.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class CouponEntity implements Serializable {

    public String id;
    public String businessId;
    public String strCode;
    public String strDescription;
    public String strDiscountType;
    public double amtCouponValue;
    public Date dateExpiration;
    public Integer amtRedemptionLimit;
    public Integer amtRedemptionCount;
    public boolean flgSameClientReusage;
    public boolean flgActive;
    public Date dateCreation;
    public Date dateModification;

    public CouponEntity() {
        this.id = "";
        this.businessId = "";
        this.strCode = "";
        this.strDescription = "";
        this.strDiscountType = "";
        this.amtCouponValue = 0.0;
        this.dateExpiration = new Date();
        this.amtRedemptionLimit = 0;
        this.amtRedemptionCount = 0;
        this.flgSameClientReusage = false;
        this.flgActive = false;
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }

    public CouponEntity(JSONObject jsonObject) {

        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.strCode = jsonObject.optString("strCode");
        this.strDescription = jsonObject.optString("strDescription");
        this.strDiscountType = jsonObject.optString("strDiscountType");
        this.amtCouponValue = jsonObject.optDouble("amtCouponValue", 0.0);
        this.dateExpiration = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateExpiration"));
        this.amtRedemptionLimit = jsonObject.optInt("amtRedemptionLimit");
        this.amtRedemptionCount = jsonObject.optInt("amtRedemptionCount");
        this.flgSameClientReusage = jsonObject.optBoolean("flgSameClientReusage");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"));
    }

    public CouponEntity(
            String id, String businessId, String strCode, String strDescription, String strDiscountType,
            double amtCouponValue, Date dateExpiration, Integer amtRedemptionLimit, Integer amtRedemptionCount,
            boolean flgSameClientReusage, boolean flgActive, Date dateCreation, Date dateModification) {
        this.id = id;
        this.businessId = businessId;
        this.strCode = strCode;
        this.strDescription = strDescription;
        this.strDiscountType = strDiscountType;
        this.amtCouponValue = amtCouponValue;
        this.dateExpiration = dateExpiration;
        this.amtRedemptionLimit = amtRedemptionLimit;
        this.amtRedemptionCount = amtRedemptionCount;
        this.flgSameClientReusage = flgSameClientReusage;
        this.flgActive = flgActive;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public static List<CouponEntity> getCollectionFromJsonArray(JSONArray array) {
        List<CouponEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new CouponEntity((JSONObject) obj));
        });
        return entities;
    }

    public static CouponEntity getSingleFromJsonArray(JSONArray array) {
        CouponEntity entity = null;
        List<CouponEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
