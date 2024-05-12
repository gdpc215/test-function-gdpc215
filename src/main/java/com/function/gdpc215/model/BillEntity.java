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

public class BillEntity {
    public String billId;
    public String businessId;
    public String userId;
    public int tableId;
    public boolean billState;
    public String couponId;
    public double amtTotalTab;
    public double amtTip;
    public double amtTotalChargeable;
    public Date dateCreation;
    public Date dateModification;

    public List<BillDetailExtendedEntity> billDetails;

    public BillEntity() {
        this.billId = "";
        this.businessId = "";
        this.userId = "";
        this.tableId = 0;
        this.billState = false;
        this.couponId = "";
        this.amtTotalTab = 0.0;
        this.amtTip = 0.0;
        this.amtTotalChargeable = 0.0;
        this.dateCreation = new Date();
        this.dateModification = new Date();

        this.billDetails = new ArrayList<BillDetailExtendedEntity>();
    }

    public BillEntity(JSONObject jsonObject) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(Utils.DATE_FORMAT);

        try {
            this.billId = jsonObject.optString("billId", "");
            this.businessId = jsonObject.optString("businessId", "");
            this.userId = jsonObject.optString("userId", "");
            this.tableId = jsonObject.optInt("tableId", 0);
            this.billState = jsonObject.optBoolean("billState", false);
            this.couponId = jsonObject.optString("couponId", "");
            this.amtTotalTab = jsonObject.optDouble("amtTotalTab", 0.0);
            this.amtTip = jsonObject.optDouble("amtTip", 0.0);
            this.amtTotalChargeable = jsonObject.optDouble("amtTotalChargeable", 0.0);
            this.dateCreation = new Date(sdfDate.parse(jsonObject.optString("dateCreation", "1970-01-01")).getTime());
            this.dateModification = new Date(sdfDate.parse(jsonObject.optString("dateModification", "1970-01-01")).getTime());

            this.billDetails = new ArrayList<BillDetailExtendedEntity>();
        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
    }

    public BillEntity(
        String billId, String businessId, String userId, int tableId, boolean billState, 
        String couponId, double amtTotalTab, double amtTip, double amtTotalChargeable, 
        Date dateCreation, Date dateModification) {
            this.billId = billId;
            this.businessId = businessId;
            this.userId = userId;
            this.tableId = tableId;
            this.billState = billState;
            this.couponId = couponId;
            this.amtTotalTab = amtTotalTab;
            this.amtTip = amtTip;
            this.amtTotalChargeable = amtTotalChargeable;
            this.dateCreation = dateCreation;
            this.dateModification = dateModification;

            this.billDetails = new ArrayList<BillDetailExtendedEntity>();
    }
    
    public static List<BillEntity> getCollectionFromJsonArray(JSONArray array) {
        List<BillEntity> entities = new ArrayList<BillEntity>();
        array.forEach(
            new Consumer<Object>() { 
                @Override
                public void accept(Object obj){ entities.add(new BillEntity((JSONObject) obj)); } 
            }
        );
        return entities;
    }
    
    public static BillEntity getSingleFromJsonArray(JSONArray array) {
        BillEntity entity = null;
        List<BillEntity> entities = getCollectionFromJsonArray(array);
        if (entities.size() > 0) {
            entity = entities.get(0);
        }
        return entity;
    }
}
