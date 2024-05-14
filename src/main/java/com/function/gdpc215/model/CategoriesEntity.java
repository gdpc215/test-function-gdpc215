package com.function.gdpc215.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class CategoriesEntity {

    public String id;
    public String businessId;
    public String strName;
    public String strDayAvailability;
    public int orderNumber;
    public Date dateCreation;
    public Date dateModification;

    public CategoriesEntity() {
        this.id = "";
        this.businessId = "";
        this.strName = "";
        this.strDayAvailability = "";
        this.orderNumber = 0;
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }

    public CategoriesEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.strName = jsonObject.optString("strName");
        this.strDayAvailability = jsonObject.optString("strDayAvailability");
        this.orderNumber = jsonObject.optInt("orderNumber", 0);
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"));
    }

    public CategoriesEntity(
            String id, String businessId, String strName, String strDayAvailability,
            int orderNumber, Date dateCreation, Date dateModification) {
        this.id = id;
        this.businessId = businessId;
        this.strName = strName;
        this.strDayAvailability = strDayAvailability;
        this.orderNumber = orderNumber;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public static List<CategoriesEntity> getCollectionFromJsonArray(JSONArray array) {
        List<CategoriesEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new CategoriesEntity((JSONObject) obj));
        });
        return entities;
    }

    public static CategoriesEntity getSingleFromJsonArray(JSONArray array) {
        CategoriesEntity entity = null;
        List<CategoriesEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
