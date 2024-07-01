package com.function.gdpc215.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class AvailabilityEntity implements Serializable {

    public String id;
    public String businessId;
    public String strName;
    public String strDescription;
    public boolean flgShowDescription;
    public boolean flgActive;
    public String strAvailableDays;
    public Time timeAvailabilityStart;
    public Time timeAvailabilityEnd;
    public LocalDateTime dateValidityStart;
    public LocalDateTime dateValidityEnd;
    public LocalDateTime dateCreation;
    public LocalDateTime dateModification;

    public AvailabilityEntity() {
        this.id = "";
        this.businessId = "";
        this.strName = "";
        this.strDescription = "";
        this.flgShowDescription = false;
        this.flgActive = false;
        this.strAvailableDays = "";
        this.timeAvailabilityStart = new Time(0);
        this.timeAvailabilityEnd = new Time(0);
        this.dateValidityStart = LocalDateTime.MIN;
        this.dateValidityEnd = LocalDateTime.MIN;
        this.dateCreation = LocalDateTime.MIN;
        this.dateModification = LocalDateTime.MIN;
    }

    public AvailabilityEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.strName = jsonObject.optString("strName");
        this.strDescription = jsonObject.optString("strDescription");
        this.flgShowDescription = jsonObject.optBoolean("flgShowDescription");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.strAvailableDays = jsonObject.optString("strAvailableDays");
        this.timeAvailabilityStart = JsonUtilities.getParsedTime(jsonObject.optString("timeAvailabilityStart"));
        this.timeAvailabilityEnd = JsonUtilities.getParsedTime(jsonObject.optString("timeAvailabilityEnd"));
        this.dateValidityStart = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateValidityStart"));
        this.dateValidityEnd = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateValidityEnd"));
        this.dateCreation = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateModification"));
    }

    public static List<AvailabilityEntity> getCollectionFromJsonArray(JSONArray array) {
        List<AvailabilityEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new AvailabilityEntity((JSONObject) obj));
        });
        return entities;
    }

    public static AvailabilityEntity getSingleFromJsonArray(JSONArray array) {
        AvailabilityEntity entity = null;
        List<AvailabilityEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
