package com.function.gdpc215.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class BusinessEntity implements Serializable {

    public String id;
    public String strSubDomain;
    public String strName;
    public String strDescription;
    public String strSocials;
    public String strWebPage;
    public String strSegment;
    public String strSpecialties;
    public String strAddress;
    public String strContact;
    public String strMainPageImageUrl;
    public String strMainPageFullImageUrl;
    public int amtRating;
    public boolean flgActive;
    public LocalDateTime dateCreation;
    public LocalDateTime dateModification;

    public BusinessEntity() {
        this.id = "";
        this.strSubDomain = "";
        this.strName = "";
        this.strDescription = "";
        this.strSocials = "";
        this.strWebPage = "";
        this.strSegment = "";
        this.strSpecialties = "";
        this.strAddress = "";
        this.strContact = "";
        this.strMainPageImageUrl = "";
        this.strMainPageFullImageUrl = "";
        this.amtRating = 0;
        this.flgActive = false;
        this.dateCreation = LocalDateTime.MIN;
        this.dateModification = LocalDateTime.MIN;
    }

    public BusinessEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.strSubDomain = jsonObject.optString("strSubDomain");
        this.strName = jsonObject.optString("strName");
        this.strDescription = jsonObject.optString("strDescription");
        this.strSocials = jsonObject.optString("strSocials");
        this.strWebPage = jsonObject.optString("strWebPage");
        this.strSegment = jsonObject.optString("strSegment");
        this.strSpecialties = jsonObject.optString("strSpecialties");
        this.strAddress = jsonObject.optString("strAddress");
        this.strContact = jsonObject.optString("strContact");
        this.strMainPageImageUrl = jsonObject.optString("strMainPageImageUrl");
        this.strMainPageFullImageUrl = jsonObject.optString("strMainPageFullImageUrl");
        this.amtRating = jsonObject.optInt("amtRating");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.dateCreation = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateModification"));
    }

    public static List<BusinessEntity> getCollectionFromJsonArray(JSONArray array) {
        List<BusinessEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new BusinessEntity((JSONObject) obj));
        });
        return entities;
    }

    public static BusinessEntity getSingleFromJsonArray(JSONArray array) {
        BusinessEntity entity = null;
        List<BusinessEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
