package com.function.gdpc215.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class ConfigEntity implements Serializable {

    public String id;
    public String businessId;
    public String strValue;
    public String strDescription;
    public boolean flgActive;
    public LocalDateTime dateCreation;
    public LocalDateTime dateModification;

    public ConfigEntity() {
        this.id = "";
        this.businessId = "";
        this.strValue = "";
        this.strDescription = "";
        this.flgActive = false;
        this.dateCreation = LocalDateTime.MIN;
        this.dateModification = LocalDateTime.MIN;
    }

    public ConfigEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.strValue = jsonObject.optString("strValue");
        this.strDescription = jsonObject.optString("strDescription");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.dateCreation = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateModification"));
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
