package com.function.gdpc215.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;

public class ProductEntity implements Serializable {

    public String id;
    public String businessId;
    public String categoryId;
    public String strName;
    public String strDescription;
    public double amtPrice;
    public Integer amtPreparationTime;
    public String flgDispatchType;
    public Double amtMinSaleWeight;
    public String strMinSaleWeightMeasure;
    public boolean flgHasStock;
    public String strImagePath;
    public String strAllergies;
    public String strCaloricInfo;
    public LocalDateTime dateCreation;
    public LocalDateTime dateModification;

    public ProductEntity() {
        this.id = "";
        this.businessId = "";
        this.categoryId = "";
        this.strName = "";
        this.strDescription = "";
        this.amtPrice = 0.0;
        this.amtPreparationTime = 0;
        this.flgDispatchType = "";
        this.amtMinSaleWeight = 0.0;
        this.strMinSaleWeightMeasure = "";
        this.flgHasStock = false;
        this.strImagePath = "";
        this.strAllergies = "";
        this.strCaloricInfo = "";
        this.dateCreation = LocalDateTime.MIN;
        this.dateModification = LocalDateTime.MIN;
    }

    public ProductEntity(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.businessId = jsonObject.optString("businessId");
        this.categoryId = jsonObject.optString("categoryId");
        this.strName = jsonObject.optString("strName");
        this.strDescription = jsonObject.optString("strDescription");
        this.amtPrice = jsonObject.optDouble("amtPrice", 0.0);
        this.amtPreparationTime = jsonObject.optInt("amtPreparationTime");
        this.flgDispatchType = jsonObject.optString("flgDispatchType");
        this.amtMinSaleWeight = jsonObject.optDouble("amtMinSaleWeight", 0.0);
        this.strMinSaleWeightMeasure = jsonObject.optString("strMinSaleWeightMeasure");
        this.flgHasStock = jsonObject.optBoolean("flgHasStock");
        this.strImagePath = jsonObject.optString("strImagePath");
        this.strAllergies = jsonObject.optString("strAllergies");
        this.strCaloricInfo = jsonObject.optString("strCaloricInfo");
        this.dateCreation = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateCreation"));
        this.dateModification = JsonUtilities.getParsedLocalDateTime(jsonObject.optString("dateModification"));
    }

    public static List<ProductEntity> getCollectionFromJsonArray(JSONArray array) {
        List<ProductEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new ProductEntity((JSONObject) obj));
        });
        return entities;
    }

    public static ProductEntity getSingleFromJsonArray(JSONArray array) {
        ProductEntity entity = null;
        List<ProductEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
