package com.function.gdpc215.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class TableEntity implements Serializable {

    public String id;
    public String businessId;
    public String tableNumber;
    public boolean flgActive;
    public int amtActiveSessions;
    public Date dateCreation;
    public Date dateModification;

    public TableEntity() {
        this.id = "";
        this.businessId = "";
        this.tableNumber = "";
        this.flgActive = false;
        this.amtActiveSessions = 0;
        this.dateCreation = new Date(0);
        this.dateModification = new Date(0);
    }

    public TableEntity(String id, String businessId, String tableNumber, boolean flgActive, int amtActiveSessions,
            Date dateCreation, Date dateModification) {
        this.id = id;
        this.businessId = businessId;
        this.tableNumber = tableNumber;
        this.flgActive = flgActive;
        this.amtActiveSessions = amtActiveSessions;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public TableEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.tableNumber = jsonObject.optString("tableNumber");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.amtActiveSessions = jsonObject.optInt("amtActiveSessions");
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"));
    }

    public static List<TableEntity> getCollectionFromJsonArray(JSONArray array) {
        List<TableEntity> entities = new ArrayList<>();
        if (array.length() > 0) {
            array.forEach((Object obj) -> {
                entities.add(new TableEntity((JSONObject) obj));
            });
        }
        return entities;
    }

    public static TableEntity getSingleFromJsonArray(JSONArray array) {
        TableEntity entity = null;
        List<TableEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
