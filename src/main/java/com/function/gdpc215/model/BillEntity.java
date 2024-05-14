package com.function.gdpc215.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class BillEntity {

    public String billId;
    public String businessId;
    public String userId;
    public int tableId;
    public boolean billState;
    public String couponId;
    public double amtTotalTab;
    public double amtTip;
    public double amtTotalChargeable;
    public Date dateCreation;
    public Date dateModification;

    public List<BillDetailExtendedEntity> billDetails;

    public BillEntity() {
        this.billId = "";
        this.businessId = "";
        this.userId = "";
        this.tableId = 0;
        this.billState = false;
        this.couponId = "";
        this.amtTotalTab = 0.0;
        this.amtTip = 0.0;
        this.amtTotalChargeable = 0.0;
        this.dateCreation = new Date();
        this.dateModification = new Date();

        this.billDetails = new ArrayList<>();
    }

    public BillEntity(JSONObject jsonObject) {

        this.billId = jsonObject.optString("billId");
        this.businessId = jsonObject.optString("businessId");
        this.userId = jsonObject.optString("userId");
        this.tableId = jsonObject.optInt("tableId", 0);
        this.billState = jsonObject.optBoolean("billState");
        this.couponId = jsonObject.optString("couponId");
        this.amtTotalTab = jsonObject.optDouble("amtTotalTab");
        this.amtTip = jsonObject.optDouble("amtTip");
        this.amtTotalChargeable = jsonObject.optDouble("amtTotalChargeable");
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"));

        this.billDetails = new ArrayList<>();
    }

    public BillEntity(
            String billId, String businessId, String userId, int tableId, boolean billState,
            String couponId, double amtTotalTab, double amtTip, double amtTotalChargeable,
            Date dateCreation, Date dateModification) {
        this.billId = billId;
        this.businessId = businessId;
        this.userId = userId;
        this.tableId = tableId;
        this.billState = billState;
        this.couponId = couponId;
        this.amtTotalTab = amtTotalTab;
        this.amtTip = amtTip;
        this.amtTotalChargeable = amtTotalChargeable;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;

        this.billDetails = new ArrayList<>();
    }

    public static List<BillEntity> getCollectionFromJsonArray(JSONArray array) {
        List<BillEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new BillEntity((JSONObject) obj));
        });
        return entities;
    }

    public static BillEntity getSingleFromJsonArray(JSONArray array) {
        BillEntity entity = null;
        List<BillEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
