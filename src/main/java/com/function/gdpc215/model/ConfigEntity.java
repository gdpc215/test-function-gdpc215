package com.function.gdpc215.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class ConfigEntity {

    public String id;
    public String businessId;
    public String strValue;
    public String strDescription;
    public boolean flgActive;
    public Date dateCreation;
    public Date dateModification;

    public ConfigEntity() {
        this.id = "";
        this.businessId = "";
        this.strValue = "";
        this.strDescription = "";
        this.flgActive = false;
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }

    public ConfigEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.strValue = jsonObject.optString("strValue");
        this.strDescription = jsonObject.optString("strDescription");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"));
    }

    public ConfigEntity(String id, String businessId, String strValue, String strDescription, boolean flgActive, Date dateCreation, Date dateModification) {
        this.id = id;
        this.businessId = businessId;
        this.strValue = strValue;
        this.strDescription = strDescription;
        this.flgActive = flgActive;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public static List<ConfigEntity> getCollectionFromJsonArray(JSONArray array) {
        List<ConfigEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new ConfigEntity((JSONObject) obj));
        });
        return entities;
    }

    public static ConfigEntity getSingleFromJsonArray(JSONArray array) {
        ConfigEntity entity = null;
        List<ConfigEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}