package com.function.gdpc215.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.LogUtils;
import com.function.gdpc215.utils.Utils;

public class CategoriesEntity {
    public String id;
    public String businessId;
    public String strName;
    public String strDayAvailability;
    public int orderNumber;
    public Date dateCreation;
    public Date dateModification;

    public CategoriesEntity() {
        this.id = "";
        this.businessId = "";
        this.strName = "";
        this.strDayAvailability = "";
        this.orderNumber = 0;
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }

    public CategoriesEntity(JSONObject jsonObject) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(Utils.DATE_FORMAT);

        try {
            this.id = jsonObject.optString("id", "");
            this.businessId = jsonObject.optString("businessId", "");
            this.strName = jsonObject.optString("strName", "");
            this.strDayAvailability = jsonObject.optString("strDayAvailability", "");
            this.orderNumber = jsonObject.optInt("orderNumber", 0);
            this.dateCreation = new Date(sdfDate.parse(jsonObject.optString("dateCreation", "1970-01-01")).getTime());
            this.dateModification = new Date(sdfDate.parse(jsonObject.optString("dateModification", "1970-01-01")).getTime());
        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
    }

    public CategoriesEntity(
        String id, String businessId, String strName, String strDayAvailability, 
        int orderNumber, Date dateCreation, Date dateModification) {
            this.id = id; 
            this.businessId = businessId;
            this.strName = strName;
            this.strDayAvailability = strDayAvailability;
            this.orderNumber = orderNumber;
            this.dateCreation = dateCreation;
            this.dateModification = dateModification;
    }
    
    public static List<CategoriesEntity> getCollectionFromJsonArray(JSONArray array) {
        List<CategoriesEntity> entities = new ArrayList<CategoriesEntity>();
        array.forEach(
            new Consumer<Object>() { 
                @Override
                public void accept(Object obj){ entities.add(new CategoriesEntity((JSONObject) obj)); } 
            }
        );
        return entities;
    }
    
    public static CategoriesEntity getSingleFromJsonArray(JSONArray array) {
        CategoriesEntity entity = null;
        List<CategoriesEntity> entities = getCollectionFromJsonArray(array);
        if (entities.size() > 0) {
            entity = entities.get(0);
        }
        return entity;
    }
}

