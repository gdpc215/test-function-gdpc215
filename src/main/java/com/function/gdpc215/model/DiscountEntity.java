package com.function.gdpc215.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class DiscountEntity {

    public String id;
    public String businessId;
    public String strName;
    public String strDescription;
    public double amtDiscount;
    public String strDiscountType;
    public boolean flgActive;
    public String strAvailableDays;
    public Time timeAvailabilityStart;
    public Time timeAvailabilityEnd;
    public Date dateValidityStart;
    public Date dateValidityEnd;
    public Date dateCreation;
    public Date dateModification;

    public DiscountEntity() {
        this.id = "";
        this.businessId = "";
        this.strName = "";
        this.strDescription = "";
        this.amtDiscount = 0.0;
        this.strDiscountType = "";
        this.flgActive = false;
        this.strAvailableDays = "";
        this.timeAvailabilityStart = new Time(0);
        this.timeAvailabilityEnd = new Time(0);
        this.dateValidityStart = new Date();
        this.dateValidityEnd = new Date();
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }

    public DiscountEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.strName = jsonObject.optString("strName");
        this.strDescription = jsonObject.optString("strDescription");
        this.amtDiscount = jsonObject.optDouble("amtDiscount");
        this.strDiscountType = jsonObject.optString("strDiscountType");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.strAvailableDays = jsonObject.optString("strAvailableDays");
        this.timeAvailabilityStart = JsonUtilities.getTimeFromJsonString(jsonObject.optString("timeAvailabilityStart", "00:00:00"));
        this.timeAvailabilityEnd = JsonUtilities.getTimeFromJsonString(jsonObject.optString("timeAvailabilityEnd", "00:00:00"));
        this.dateValidityStart = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateValidityStart"));
        this.dateValidityEnd = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateValidityEnd"));
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"));
    }

    public DiscountEntity(
            String id, String businessId, String strName, String strDescription, double amtDiscount,
            String strDiscountType, boolean flgActive, String strAvailableDays, Time timeAvailabilityStart,
            Time timeAvailabilityEnd, Date dateValidityStart, Date dateValidityEnd, Date dateCreation, Date dateModification) {
        this.id = id;
        this.businessId = businessId;
        this.strName = strName;
        this.strDescription = strDescription;
        this.amtDiscount = amtDiscount;
        this.strDiscountType = strDiscountType;
        this.flgActive = flgActive;
        this.strAvailableDays = strAvailableDays;
        this.timeAvailabilityStart = timeAvailabilityStart;
        this.timeAvailabilityEnd = timeAvailabilityEnd;
        this.dateValidityStart = dateValidityStart;
        this.dateValidityEnd = dateValidityEnd;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public static List<DiscountEntity> getCollectionFromJsonArray(JSONArray array) {
        List<DiscountEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new DiscountEntity((JSONObject) obj));
        });
        return entities;
    }

    public static DiscountEntity getSingleFromJsonArray(JSONArray array) {
        DiscountEntity entity = null;
        List<DiscountEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}