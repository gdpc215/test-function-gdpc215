package com.function.gdpc215.logic;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.model.CouponEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Coupon {
    
    public static Object hubCoupon (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
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

    private static Object fnCoupon_Get(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCoupon_GetByID(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        CouponEntity entity = CouponEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }

    private static Object fnCoupon_Get_ValidateCode(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCoupon_Get_ValidateCode(?, ?) }");
        spCall.setString(1, request.getQueryParameters().get("business-id"));
        spCall.setString(2, request.getQueryParameters().get("str-code"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        CouponEntity entity = CouponEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }
    
    private static Object fnCoupon_Get_ValidateCodeForClient(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCoupon_Get_ValidateCodeForClient(?, ?, ?) }");
        spCall.setString(1, request.getQueryParameters().get("business-id"));
        spCall.setString(2, request.getQueryParameters().get("str-code"));
        spCall.setString(3, request.getQueryParameters().get("client-id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        CouponEntity entity = CouponEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }

    private static Object fnCoupon_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spCoupon_Insert(?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.optString("businessId"));
            spCall.setString(2, json.optString("strCode"));
            spCall.setString(3, json.optString("strDescription"));
            spCall.setString(4, json.optString("strDiscountType"));
            spCall.setBigDecimal(5, new BigDecimal(json.optString("amtCouponValue")));
            spCall.setDate(6, Date.valueOf(json.optString("dateExpiration")));
            spCall.setInt(7, json.getInt("amtRedemptionLimit"));
            spCall.setBoolean(8, json.optBoolean("flgSameClientReusage"));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnCoupon_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spCoupon_Update(?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.optString("id"));
            spCall.setString(2, json.optString("strCode"));
            spCall.setString(3, json.optString("strDescription"));
            spCall.setString(4, json.optString("strDiscountType"));
            spCall.setBigDecimal(5, new BigDecimal(json.optString("amtCouponValue")));
            spCall.setDate(6, Date.valueOf(json.optString("dateExpiration")));
            spCall.setInt(7, json.getInt("amtRedemptionLimit"));
            spCall.setBoolean(8, json.optBoolean("flgSameClientReusage"));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnCoupon_Delete(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCoupon_Delete(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute the procedure
        spCall.executeUpdate();

        return null;
    }
    
}
