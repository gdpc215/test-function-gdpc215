package com.function.gdpc215.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.LogUtils;

public class BillDetailExtendedEntity implements Serializable {
    public BillDetailEntity billDetailsEntity;

    public String strName;
    public String fullImgPath;
    public String strDispatchTypeDescription;
    public double amtMinSaleWeight;
    public String strMinSaleWeightMeasure;
    public String strWeightDescription;


    public BillDetailExtendedEntity() {
        this.billDetailsEntity = new BillDetailEntity();
        this.strName = "";
        this.fullImgPath = "";
        this.strDispatchTypeDescription = "";
        this.amtMinSaleWeight = 0.0;
        this.strMinSaleWeightMeasure = "";
        this.strWeightDescription = "";
    }

    public BillDetailExtendedEntity(JSONObject jsonObject) {
        try {
            this.billDetailsEntity = new BillDetailEntity(jsonObject);

            this.strName = jsonObject.optString("strName");
            this.fullImgPath = jsonObject.optString("fullImgPath");
            this.strDispatchTypeDescription = jsonObject.optString("strDispatchTypeDescription");
            this.amtMinSaleWeight = jsonObject.optDouble("amtMinSaleWeight");
            this.strMinSaleWeightMeasure = jsonObject.optString("strMinSaleWeightMeasure");
            this.strWeightDescription = jsonObject.optString("strWeightDescription");

        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
    }

    public BillDetailExtendedEntity(
        String billDetailId, String billId, String menuItemId, double amtListedPrice,
        String strSpecialRequirements, double amtAmount, Date dateCreation, Date dateModification,
        String strName, String fullImgPath, String strDispatchTypeDescription,
        double amtMinSaleWeight, String strMinSaleWeightMeasure, String strWeightDescription) {

            this.billDetailsEntity = new BillDetailEntity(
                billDetailId, billId, menuItemId, amtListedPrice, strSpecialRequirements, 
                amtAmount, dateCreation, dateModification);

            this.strName = strName;
            this.fullImgPath = fullImgPath;
            this.strDispatchTypeDescription = strDispatchTypeDescription;
            this.amtMinSaleWeight = amtMinSaleWeight;
            this.strMinSaleWeightMeasure = strMinSaleWeightMeasure;
            this.strWeightDescription = strWeightDescription;

    }
    
    public static List<BillDetailExtendedEntity> getCollectionFromJsonArray(JSONArray array) {
        List<BillDetailExtendedEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new BillDetailExtendedEntity((JSONObject) obj));
        });
        return entities;
    }
    
    public static List<BillDetailEntity> getBaseObjCollectionFromJsonArray(JSONArray array) {
        List<BillDetailEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            BillDetailEntity ent = (new BillDetailExtendedEntity((JSONObject) obj)).billDetailsEntity;
            entities.add(ent);
        });
        return entities;
    }
    
    public static BillDetailExtendedEntity getSingleFromJsonArray(JSONArray array) {
        BillDetailExtendedEntity entity = null;
        List<BillDetailExtendedEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
    
    public static BillDetailEntity getBaseObjSingleFromJsonArray(JSONArray array) {
        BillDetailEntity entity = null;
        List<BillDetailEntity> entities = getBaseObjCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
