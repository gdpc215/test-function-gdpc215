package com.function.gdpc215.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    public int amtRating;
    public boolean flgActive;
    public Date dateCreation;
    public Date dateModification;

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
        this.amtRating = 0;
        this.flgActive = false;
        this.dateCreation = new Date();
        this.dateModification = new Date();
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
        this.amtRating = jsonObject.optInt("amtRating");
        this.flgActive = jsonObject.optBoolean("flgActive");
        this.dateCreation = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getDateFromJsonString(jsonObject.optString("dateModification"));
    }

    public BusinessEntity(
            String id, String strSubDomain, String strName, String strDescription, String strSocials, String strWebPage,
            String strSegment, String strSpecialties, String strAddress, String strContact, int amtRating, boolean flgActive,
            Date dateCreation, Date dateModification) {
        this.id = id;
        this.strName = strName;
        this.strSubDomain = strSubDomain;
        this.strDescription = strDescription;
        this.strSocials = strSocials;
        this.strWebPage = strWebPage;
        this.strSegment = strSegment;
        this.strSpecialties = strSpecialties;
        this.strAddress = strAddress;
        this.strContact = strContact;
        this.amtRating = amtRating;
        this.flgActive = flgActive;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
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
