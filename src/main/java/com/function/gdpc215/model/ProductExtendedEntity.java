package com.function.gdpc215.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.utils.LogUtils;

public class ProductExtendedEntity implements Serializable {

    public ProductEntity productEntity;

    public double amtListedPrice;
    public String strWeightDescription;
    public String strDispatchTypeDescription;
    public String strFullImagePath;
    public String strCategoryName;
    public int intCategoryOrderNumber;
    public String strCategoryDayAvailability;
    public boolean flgHasDiscount;
    public boolean flgCategoryValid;
    public boolean flgIsAvailable;
    public boolean flgFinalValidation;

    public String strAvailabilityDetails;
    public List<AvailabilityEntity> listAvailabilityDetails;

    public ProductExtendedEntity() {
        this.productEntity = new ProductEntity();

        this.amtListedPrice = 0;
        this.strWeightDescription = "";
        this.strDispatchTypeDescription = "";
        this.strFullImagePath = "";
        this.strCategoryName = "";
        this.intCategoryOrderNumber = 0;
        this.strCategoryDayAvailability = "";
        this.flgHasDiscount = false;
        this.flgCategoryValid = false;
        this.flgIsAvailable = false;
        this.flgFinalValidation = false;

        this.strAvailabilityDetails = "";
        this.listAvailabilityDetails = new ArrayList<>();
    }

    public ProductExtendedEntity(JSONObject jsonObject) {
        try {
            this.productEntity = new ProductEntity(jsonObject);

            this.amtListedPrice = jsonObject.optDouble("amtListedPrice", 0.0);
            this.strWeightDescription = jsonObject.optString("strWeightDescription");
            this.strDispatchTypeDescription = jsonObject.optString("strDispatchTypeDescription");
            this.strFullImagePath = jsonObject.optString("strFullImagePath");
            this.strCategoryName = jsonObject.optString("strCategoryName");
            this.intCategoryOrderNumber = jsonObject.optInt("intCategoryOrderNumber");
            this.strCategoryDayAvailability = jsonObject.optString("strCategoryDayAvailability");
            this.flgHasDiscount = jsonObject.optBoolean("flgHasDiscount");
            this.flgCategoryValid = jsonObject.optBoolean("flgCategoryValid");
            this.flgIsAvailable = jsonObject.optBoolean("flgIsAvailable");
            this.flgFinalValidation = jsonObject.optBoolean("flgFinalValidation");

            this.strAvailabilityDetails = jsonObject.optString("strAvailabilityDetails");
            populateAvailabilityArray();
        } catch (JSONException e) {
            LogUtils.ExceptionHandler(e);
        }
    }

    private void populateAvailabilityArray() {
        this.listAvailabilityDetails = new ArrayList<>();
        JSONArray jsonArrayAvailabilityDetails = new JSONArray(this.strAvailabilityDetails);
        if (!jsonArrayAvailabilityDetails.isEmpty()) {
            for (int i = 0; i < jsonArrayAvailabilityDetails.length(); i++) {
                JSONObject availabilityObj = jsonArrayAvailabilityDetails.optJSONObject(i);
                if (availabilityObj != null) {
                    this.listAvailabilityDetails.add(new AvailabilityEntity(availabilityObj));
                }
            }
        }
        this.strAvailabilityDetails = null;
    }

    public static List<ProductExtendedEntity> getCollectionFromJsonArray(JSONArray array) {
        List<ProductExtendedEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            entities.add(new ProductExtendedEntity((JSONObject) obj));
        });
        return entities;
    }

    public static List<ProductEntity> getBaseObjCollectionFromJsonArray(JSONArray array) {
        List<ProductEntity> entities = new ArrayList<>();
        array.forEach((Object obj) -> {
            ProductEntity ent = (new ProductExtendedEntity((JSONObject) obj)).productEntity;
            entities.add(ent);
        });
        return entities;
    }

    public static ProductExtendedEntity getSingleFromJsonArray(JSONArray array) {
        ProductExtendedEntity entity = null;
        List<ProductExtendedEntity> entities = getCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }

    public static ProductEntity getBaseObjSingleFromJsonArray(JSONArray array) {
        ProductEntity entity = null;
        List<ProductEntity> entities = getBaseObjCollectionFromJsonArray(array);
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }
}
