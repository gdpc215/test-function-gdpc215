package com.function.gdpc215.logic;

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.CouponDB;
import com.function.gdpc215.model.CouponEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Coupon {

    public static Object hubCoupon(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnCoupon_Get(request, connectionString);
            case "validate-code" -> fnCoupon_Get_ValidateCode(request, connectionString);
            case "validate-code-for-client" -> fnCoupon_Get_ValidateCodeForClient(request, connectionString);
            case "insert" -> fnCoupon_Insert(request, connectionString);
            case "update" -> fnCoupon_Update(request, connectionString);
            case "delete" -> fnCoupon_Delete(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnCoupon_Get(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String couponId = request.getQueryParameters().get("id");

        CouponEntity entity = CouponDB.fnCoupon_Get(connectionString, couponId);
        return entity;
    }

    private static Object fnCoupon_Get_ValidateCode(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        String businessId = request.getQueryParameters().get("business-id");
        String strCode = request.getQueryParameters().get("str-code");

        CouponEntity entity = CouponDB.fnCoupon_Get_ValidateCode(connectionString, businessId, strCode);
        return entity;
    }

    private static Object fnCoupon_Get_ValidateCodeForClient(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        String businessId = request.getQueryParameters().get("business-id");
        String strCode = request.getQueryParameters().get("str-code");
        String userId = request.getQueryParameters().get("user-id");

        CouponEntity entity = CouponDB.fnCoupon_Get_ValidateCodeForClient(connectionString, businessId, strCode,
                userId);
        return entity;
    }

    private static Object fnCoupon_Insert(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            CouponEntity entity = new CouponEntity(jsonBody);
            CouponDB.fnCoupon_Insert(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnCoupon_Update(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            CouponEntity entity = new CouponEntity(jsonBody);
            CouponDB.fnCoupon_Update(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnCoupon_Delete(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Prepare statement
        String couponId = request.getQueryParameters().get("id");

        CouponDB.fnCoupon_Delete(connectionString, couponId);
        return null;
    }

}
