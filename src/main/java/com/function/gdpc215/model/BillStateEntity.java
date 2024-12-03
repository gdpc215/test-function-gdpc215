package com.function.gdpc215.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class BillStateEntity implements Serializable {

    public int billState;
    public int billGeneralState;
    public String stateDescription;
    public String waiterTabMenuLabel;
    public LocalDateTime dateCreation;
    public LocalDateTime dateModification;

    public List<BillDetailExtendedEntity> billDetails;

    public BillStateEntity() {
        this.billState = 0;
        this.billGeneralState = 0;
        this.stateDescription = "";
        this.waiterTabMenuLabel = "";
        this.dateCreation = LocalDateTime.MIN;
        this.dateModification = LocalDateTime.MIN;
    }

    public BillStateEntity(JSONObject jsonObject) {

        this.billState = jsonObject.optInt("billState");
        this.billGeneralState = jsonObject.optInt("billGeneralState");
        this.stateDescription = jsonObject.optString("stateDescription");
        this.waiterTabMenuLabel = jsonObject.optString("waiterTabMenuLabel");
        this.dateCreation = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateModification"));
    }

    public static List<BillStateEntity> getCollectionFromJsonArray(JSONArray array) {
        List<BillStateEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new BillStateEntity((JSONObject) obj));
        });
        return entities;
    }

    public static BillStateEntity getSingleFromJsonArray(JSONArray array) {
        BillStateEntity entity = null;
        List<BillStateEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
