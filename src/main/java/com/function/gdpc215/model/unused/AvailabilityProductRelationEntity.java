package com.function.gdpc215.model.unused;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.function.gdpc215.utils.LogUtils;
import com.function.gdpc215.utils.Utils;

public class AvailabilityProductRelationEntity {
    public String availabilityId;
    public String productId;
    public Date dateCreation;

    public AvailabilityProductRelationEntity() {
        this.availabilityId = "";
        this.productId = "";
        this.dateCreation = new Date();
    }

    public AvailabilityProductRelationEntity(JSONObject jsonObject) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(Utils.DATE_FORMAT);

        try {
            this.availabilityId = jsonObject.optString("availabilityId", "");
            this.productId = jsonObject.optString("productId", "");
            this.dateCreation = new Date(sdfDate.parse(jsonObject.optString("dateCreation", "1970-01-01")).getTime());
        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
    }

    public AvailabilityProductRelationEntity(String availabilityId, String productId, Date dateCreation) {
        this.availabilityId = availabilityId;
        this.productId = productId;
        this.dateCreation = dateCreation;
    }
}
