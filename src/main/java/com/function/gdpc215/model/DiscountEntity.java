package com.function.gdpc215.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class DiscountEntity implements Serializable {

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
    public LocalDateTime dateValidityStart;
    public LocalDateTime dateValidityEnd;
    public LocalDateTime dateCreation;
    public LocalDateTime dateModification;

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
        this.dateValidityStart = LocalDateTime.MIN;
        this.dateValidityEnd = LocalDateTime.MIN;
        this.dateCreation = LocalDateTime.MIN;
        this.dateModification = LocalDateTime.MIN;
    }

    public DiscountEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.strName = jsonObject.optString("strName");
        this.strDescription = jsonObject.optString("strDescription");
        this.amtDiscount = jsonObject.optDouble("amtDiscount", 0.0);
        this.strDiscountType = jsonObject.optString("strDiscountType");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.strAvailableDays = jsonObject.optString("strAvailableDays");
        this.timeAvailabilityStart = JsonUtilities
                .getParsedTime(jsonObject.optString("timeAvailabilityStart", "00:00:00"));
        this.timeAvailabilityEnd = JsonUtilities
                .getParsedTime(jsonObject.optString("timeAvailabilityEnd", "00:00:00"));
        this.dateValidityStart = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateValidityStart"));
        this.dateValidityEnd = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateValidityEnd"));
        this.dateCreation = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateModification"));
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
