package com.function.gdpc215.model;

import java.io.Serializable;
import java.util.ArrayList;
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

    public BillDetailExtendedEntity(JSONObject jsonObject, boolean sameLevelData) {
        try {
            this.billDetailsEntity = new BillDetailEntity((sameLevelData) ? jsonObject : jsonObject.optJSONObject("billDetailsEntity"));

            this.strName = jsonObject.optString("strName");
            this.fullImgPath = jsonObject.optString("fullImgPath");
            this.strDispatchTypeDescription = jsonObject.optString("strDispatchTypeDescription");
            this.amtMinSaleWeight = jsonObject.optDouble("amtMinSaleWeight", 0.0);
            this.strMinSaleWeightMeasure = jsonObject.optString("strMinSaleWeightMeasure");
            this.strWeightDescription = jsonObject.optString("strWeightDescription");

        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
    }
    
    public static List<BillDetailExtendedEntity> getCollectionFromJsonArray(JSONArray array, boolean sameLevelData) {
        List<BillDetailExtendedEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new BillDetailExtendedEntity((JSONObject) obj, sameLevelData));
        });
        return entities;
    }
    
    public static List<BillDetailEntity> getBaseObjCollectionFromJsonArray(JSONArray array, boolean sameLevelData) {
        List<BillDetailEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            BillDetailEntity ent = (new BillDetailExtendedEntity((JSONObject) obj, sameLevelData)).billDetailsEntity;
            entities.add(ent);
        });
        return entities;
    }
    
    public static BillDetailExtendedEntity getSingleFromJsonArray(JSONArray array, boolean sameLevelData) {
        BillDetailExtendedEntity entity = null;
        List<BillDetailExtendedEntity> entities = getCollectionFromJsonArray(array, sameLevelData);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
    
    public static BillDetailEntity getBaseObjSingleFromJsonArray(JSONArray array, boolean sameLevelData) {
        BillDetailEntity entity = null;
        List<BillDetailEntity> entities = getBaseObjCollectionFromJsonArray(array, sameLevelData);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
