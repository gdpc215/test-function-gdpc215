package com.function.gdpc215.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class BillDetailEntity implements Serializable {

    public String billDetailId;
    public String billId;
    public String menuItemId;
    public double amtListedPrice;
    public String strSpecialRequirements;
    public double amtAmount;
    public Date dateCreation;
    public Date dateModification;

    public BillDetailEntity() {
        this.billDetailId = "";
        this.billId = "";
        this.menuItemId = "";
        this.amtListedPrice = 0.0;
        this.strSpecialRequirements = "";
        this.amtAmount = 0.0;
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }

    public BillDetailEntity(JSONObject jsonObject) {
        this.billDetailId = jsonObject.optString("billDetailId");
        this.billId = jsonObject.optString("billId");
        this.menuItemId = jsonObject.optString("menuItemId");
        this.amtListedPrice = jsonObject.optDouble("amtListedPrice");
        this.strSpecialRequirements = jsonObject.optString("strSpecialRequirements");
        this.amtAmount = jsonObject.optDouble("amtAmount");
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"));

    }

    public BillDetailEntity(
            String billDetailId, String billId, String menuItemId, double amtListedPrice,
            String strSpecialRequirements, double amtAmount, Date dateCreation, Date dateModification) {
        this.billDetailId = billDetailId;
        this.billId = billId;
        this.menuItemId = menuItemId;
        this.amtListedPrice = amtListedPrice;
        this.strSpecialRequirements = strSpecialRequirements;
        this.amtAmount = amtAmount;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public static List<BillDetailEntity> getCollectionFromJsonArray(JSONArray array) {
        List<BillDetailEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new BillDetailEntity((JSONObject) obj));
        });
        return entities;
    }

    public static BillDetailEntity getSingleFromJsonArray(JSONArray array) {
        BillDetailEntity entity = null;
        List<BillDetailEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
