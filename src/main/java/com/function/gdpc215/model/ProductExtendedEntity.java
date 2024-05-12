package com.function.gdpc215.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.LogUtils;

public class ProductExtendedEntity {
    public ProductEntity productEntity;
    
    public double amtDiscountedPrice;
    public String strWeightDescription;
    public String strDispatchTypeDescription;
    public String strFullImagePath;
    public String strCategoryName;
    public int intCategoryOrderNumber;
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
        this.flgHasDiscount = false;
        this.flgCategoryValid = false;
        this.flgIsAvailable = false;
        this.flgFinalValidation = false;

        this.strAvailabilityDetails = "";
        this.listAvailabilityDetails = new ArrayList<AvailabilityEntity>();
        
    }

    // Constructor accepting all properties as parameters
    public ProductExtendedEntity(String id, String businessId, String categoryId, String strName,
            String strDescription, double amtPrice, Integer amtPreparationTime, String flgDispatchType,
            Double amtMinSaleWeight, String strMinSaleWeightMeasure, boolean flgHasStock, String strImagePath,
            String strAllergies, String strCaloricInfo, Date dateCreation, Date dateModification,
            double amtDiscountedPrice, String strWeightDescription, String strDispatchTypeDescription,
            String strFullImagePath, String strCategoryName, int intCategoryOrderNumber, boolean flgHasDiscount,
            boolean flgCategoryValid, boolean flgIsAvailable, boolean flgFinalValidation, String strAvailabilityDetails) {
        
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

            this.amtDiscountedPrice = jsonObject.getDouble("amtDiscountedPrice");
            this.strWeightDescription = jsonObject.getString("strWeightDescription");
            this.strDispatchTypeDescription = jsonObject.getString("strDispatchTypeDescription");
            this.strFullImagePath = jsonObject.getString("strFullImagePath");
            this.strCategoryName = jsonObject.getString("strCategoryName");
            this.intCategoryOrderNumber = jsonObject.getInt("intCategoryOrderNumber");
            this.flgHasDiscount = jsonObject.getBoolean("flgHasDiscount");
            this.flgCategoryValid = jsonObject.getBoolean("flgCategoryValid");
            this.flgIsAvailable = jsonObject.getBoolean("flgIsAvailable");
            this.flgFinalValidation = jsonObject.getBoolean("flgFinalValidation");
            
            this.strAvailabilityDetails = jsonObject.optString("strAvailabilityDetails");
            populateAvailabilityArray();
            
        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
    }

    private void populateAvailabilityArray() {
        this.listAvailabilityDetails = new ArrayList<>();
        JSONArray jsonArrayAvailabilityDetails = new JSONArray(this.strAvailabilityDetails);
        if (jsonArrayAvailabilityDetails != null) {
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
        List<ProductExtendedEntity> entities = new ArrayList<ProductExtendedEntity>();
        array.forEach(
            new Consumer<Object>() { 
                @Override
                public void accept(Object obj){ entities.add(new ProductExtendedEntity((JSONObject) obj)); } 
            }
        );
        return entities;
    }
    
    public static List<ProductEntity> getBaseObjCollectionFromJsonArray(JSONArray array) {
        List<ProductEntity> entities = new ArrayList<ProductEntity>();
        array.forEach(
            new Consumer<Object>() { 
                @Override
                public void accept(Object obj){ 
                    ProductEntity ent = (new ProductExtendedEntity((JSONObject) obj)).productEntity;
                    entities.add(ent); 
                } 
            }
        );
        return entities;
    }
    
    public static ProductExtendedEntity getSingleFromJsonArray(JSONArray array) {
        ProductExtendedEntity entity = null;
        List<ProductExtendedEntity> entities = getCollectionFromJsonArray(array);
        if (entities.size() > 0) {
            entity = entities.get(0);
        }
        return entity;
    }
    
    public static ProductEntity getBaseObjSingleFromJsonArray(JSONArray array) {
        ProductEntity entity = null;
        List<ProductEntity> entities = getBaseObjCollectionFromJsonArray(array);
        if (entities.size() > 0) {
            entity = entities.get(0);
        }
        return entity;
    }
}

