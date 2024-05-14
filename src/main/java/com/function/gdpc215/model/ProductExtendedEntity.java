package com.function.gdpc215.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.utils.LogUtils;

public class ProductExtendedEntity implements Serializable {

    public ProductEntity productEntity;

    public double amtDiscountedPrice;
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

    private String strAvailabilityDetails;
    public List<AvailabilityEntity> listAvailabilityDetails;

    public ProductExtendedEntity() {
        this.productEntity = new ProductEntity();

        this.amtDiscountedPrice = 0;
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

    // Constructor accepting all properties as parameters
    public ProductExtendedEntity(String id, String businessId, String categoryId, String strName,
            String strDescription, double amtPrice, Integer amtPreparationTime, String flgDispatchType,
            Double amtMinSaleWeight, String strMinSaleWeightMeasure, boolean flgHasStock, String strImagePath,
            String strAllergies, String strCaloricInfo, Date dateCreation, Date dateModification,
            double amtDiscountedPrice, String strWeightDescription, String strDispatchTypeDescription,
            String strFullImagePath, String strCategoryName, int intCategoryOrderNumber, String strCategoryDayAvailability,
            boolean flgHasDiscount, boolean flgCategoryValid, boolean flgIsAvailable, boolean flgFinalValidation,
            String strAvailabilityDetails) {

        this.productEntity = new ProductEntity(
                id, businessId, categoryId, strName, strDescription, amtPrice, amtPreparationTime, flgDispatchType, amtMinSaleWeight,
                strMinSaleWeightMeasure, flgHasStock, strImagePath, strAllergies, strCaloricInfo, dateCreation, dateModification
        );

        this.amtDiscountedPrice = amtDiscountedPrice;
        this.strWeightDescription = strWeightDescription;
        this.strDispatchTypeDescription = strDispatchTypeDescription;
        this.strFullImagePath = strFullImagePath;
        this.strCategoryName = strCategoryName;
        this.intCategoryOrderNumber = intCategoryOrderNumber;
        this.strCategoryDayAvailability = strCategoryDayAvailability;
        this.flgHasDiscount = flgHasDiscount;
        this.flgCategoryValid = flgCategoryValid;
        this.flgIsAvailable = flgIsAvailable;
        this.flgFinalValidation = flgFinalValidation;

        this.strAvailabilityDetails = strAvailabilityDetails;
        populateAvailabilityArray();
    }

    // Constructor that takes a JSONObject object and extracts the values to initialize all variables
    public ProductExtendedEntity(JSONObject jsonObject) {
        try {
            this.productEntity = new ProductEntity(jsonObject);

            this.amtDiscountedPrice = jsonObject.optDouble("amtDiscountedPrice");
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
