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

public class BillDetailEntity {
    public String billDetailId;
    public String billId;
    public String menuItemId;
    public double amtListedPrice;
    public String strSpecialRequirements;
    public double amtAmount;
    public Date dateCreation;
    public Date dateModification;

    public BillDetailEntity() {
        this.billDetailId = "";
        this.billId = "";
        this.menuItemId = "";
        this.amtListedPrice = 0.0;
        this.strSpecialRequirements = "";
        this.amtAmount = 0.0;
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }

    public BillDetailEntity(JSONObject jsonObject) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(Utils.DATE_FORMAT);

        try {
            this.billDetailId = jsonObject.optString("billDetailId", "");
            this.billId = jsonObject.optString("billId", "");
            this.menuItemId = jsonObject.optString("menuItemId", "");
            this.amtListedPrice = jsonObject.optDouble("amtListedPrice", 0.0);
            this.strSpecialRequirements = jsonObject.optString("strSpecialRequirements", "");
            this.amtAmount = jsonObject.optDouble("amtAmount", 0.0);
            this.dateCreation = new Date(sdfDate.parse(jsonObject.optString("dateCreation", "1970-01-01")).getTime());
            this.dateModification = new Date(sdfDate.parse(jsonObject.optString("dateModification", "1970-01-01")).getTime());
        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
    }

    public BillDetailEntity(
        String billDetailId, String billId, String menuItemId, double amtListedPrice, 
        String strSpecialRequirements, double amtAmount, Date dateCreation, Date dateModification) {
            this.billDetailId = billDetailId;
            this.billId = billId;
            this.menuItemId = menuItemId;
            this.amtListedPrice = amtListedPrice;
            this.strSpecialRequirements = strSpecialRequirements;
            this.amtAmount = amtAmount;
            this.dateCreation = dateCreation;
            this.dateModification = dateModification;
    }
    
    public static List<BillDetailEntity> getCollectionFromJsonArray(JSONArray array) {
        List<BillDetailEntity> entities = new ArrayList<BillDetailEntity>();
        array.forEach(
            new Consumer<Object>() { 
                @Override
                public void accept(Object obj){ entities.add(new BillDetailEntity((JSONObject) obj)); } 
            }
        );
        return entities;
    }
    
    public static BillDetailEntity getSingleFromJsonArray(JSONArray array) {
        BillDetailEntity entity = null;
        List<BillDetailEntity> entities = getCollectionFromJsonArray(array);
        if (entities.size() > 0) {
            entity = entities.get(0);
        }
        return entity;
    }
}
