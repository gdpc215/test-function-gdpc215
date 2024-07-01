package com.function.gdpc215.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class CategoriesEntity implements Serializable {

    public String id;
    public String businessId;
    public String strName;
    public String strDayAvailability;
    public int orderNumber;
    public LocalDateTime dateCreation;
    public LocalDateTime dateModification;

    public CategoriesEntity() {
        this.id = "";
        this.businessId = "";
        this.strName = "";
        this.strDayAvailability = "";
        this.orderNumber = 0;
        this.dateCreation = LocalDateTime.MIN;
        this.dateModification = LocalDateTime.MIN;
    }

    public CategoriesEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.strName = jsonObject.optString("strName");
        this.strDayAvailability = jsonObject.optString("strDayAvailability");
        this.orderNumber = jsonObject.optInt("orderNumber");
        this.dateCreation = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateModification"));
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
