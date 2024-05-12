package com.function.gdpc215.model.unused;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.function.gdpc215.utils.LogUtils;
import com.function.gdpc215.utils.Utils;

public class CouponClientRelationEntity {
    public String couponId;
    public String clientId;
    public Date dateCreation;

    public CouponClientRelationEntity() {
        this.couponId = "";
        this.clientId = "";
        this.dateCreation = new Date();
    }

    public CouponClientRelationEntity(JSONObject jsonObject) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(Utils.DATE_FORMAT);

        try {
            this.couponId = jsonObject.optString("couponId", "");
            this.clientId = jsonObject.optString("clientId", "");
            this.dateCreation = new Date(sdfDate.parse(jsonObject.optString("dateCreation", "1970-01-01")).getTime());
        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
    }

    public CouponClientRelationEntity(String couponId, String clientId, Date dateCreation) {
        this.couponId = couponId;
        this.clientId = clientId;
        this.dateCreation = dateCreation;
    }
}
