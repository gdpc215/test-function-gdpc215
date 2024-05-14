package com.function.gdpc215.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
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
    public Date dateValidityStart;
    public Date dateValidityEnd;
    public Date dateCreation;
    public Date dateModification;

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
        this.dateValidityStart = new Date();
        this.dateValidityEnd = new Date();
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }

    public AvailabilityEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.strName = jsonObject.optString("strName");
        this.strDescription = jsonObject.optString("strDescription");
        this.flgShowDescription = jsonObject.optBoolean("flgShowDescription");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.strAvailableDays = jsonObject.optString("strAvailableDays");
        this.timeAvailabilityStart = JsonUtilities.getTimeFromJsonString(jsonObject.optString("timeAvailabilityStart"));
        this.timeAvailabilityEnd = JsonUtilities.getTimeFromJsonString(jsonObject.optString("timeAvailabilityEnd"));
        this.dateValidityStart = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateValidityStart"), true);
        this.dateValidityEnd = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateValidityEnd"), true);
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"), true);
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"), true);
    }

    public AvailabilityEntity(
            String id, String businessId, String strName, String strDescription, boolean flgShowDescription,
            boolean flgActive, String strAvailableDays, Time timeAvailabilityStart, Time timeAvailabilityEnd,
            Date dateValidityStart, Date dateValidityEnd, Date dateCreation, Date dateModification) {
        this.id = id;
        this.businessId = businessId;
        this.strName = strName;
        this.strDescription = strDescription;
        this.flgShowDescription = flgShowDescription;
        this.flgActive = flgActive;
        this.strAvailableDays = strAvailableDays;
        this.timeAvailabilityStart = timeAvailabilityStart;
        this.timeAvailabilityEnd = timeAvailabilityEnd;
        this.dateValidityStart = dateValidityStart;
        this.dateValidityEnd = dateValidityEnd;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
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
