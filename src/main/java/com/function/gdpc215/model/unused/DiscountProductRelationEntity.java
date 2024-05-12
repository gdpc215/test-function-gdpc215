package com.function.gdpc215.model.unused;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.function.gdpc215.utils.LogUtils;
import com.function.gdpc215.utils.Utils;

public class DiscountProductRelationEntity {
    public String discountId;
    public String productId;
    public Date dateCreation;

    public DiscountProductRelationEntity() {
        this.discountId = "";
        this.productId = "";
        this.dateCreation = new Date();
    }

    public DiscountProductRelationEntity(JSONObject jsonObject) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(Utils.DATE_FORMAT);

        try {
            this.discountId = jsonObject.optString("discountId", "");
            this.productId = jsonObject.optString("productId", "");
            this.dateCreation = new Date(sdfDate.parse(jsonObject.optString("dateCreation", "1970-01-01")).getTime());
        } catch (Exception e) {
            LogUtils.ExceptionHandler(e);
        }
    }

    public DiscountProductRelationEntity(String discountId, String productId, Date dateCreation) {
        this.discountId = discountId;
        this.productId = productId;
        this.dateCreation = dateCreation;
    }
}
