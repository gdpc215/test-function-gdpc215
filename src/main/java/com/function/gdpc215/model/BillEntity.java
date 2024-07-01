package com.function.gdpc215.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class BillEntity implements Serializable {

    public String billId;
    public String businessId;
    public String userId;
    public String tableId;
    public boolean billState;
    public String couponId;
    public double amtTotalTab;
    public double amtTip;
    public double amtTotalChargeable;
    public LocalDateTime dateCreation;
    public LocalDateTime dateModification;

    public List<BillDetailExtendedEntity> billDetails;

    public BillEntity() {
        this.billId = "";
        this.businessId = "";
        this.userId = "";
        this.tableId = "";
        this.billState = false;
        this.couponId = "";
        this.amtTotalTab = 0.0;
        this.amtTip = 0.0;
        this.amtTotalChargeable = 0.0;
        this.dateCreation = LocalDateTime.MIN;
        this.dateModification = LocalDateTime.MIN;

        this.billDetails = new ArrayList<>();
    }

    public BillEntity(JSONObject jsonObject) {

        this.billId = jsonObject.optString("billId");
        this.businessId = jsonObject.optString("businessId");
        this.userId = jsonObject.optString("userId");
        this.tableId = jsonObject.optString("tableId");
        this.billState = jsonObject.optBoolean("billState");
        this.couponId = jsonObject.optString("couponId");
        this.amtTotalTab = jsonObject.optDouble("amtTotalTab", 0.0);
        this.amtTip = jsonObject.optDouble("amtTip", 0.0);
        this.amtTotalChargeable = jsonObject.optDouble("amtTotalChargeable", 0.0);
        this.dateCreation = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateModification"));

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
