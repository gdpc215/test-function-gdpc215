package com.function.gdpc215.logic.unused;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class CouponClientRelation {
    
    public static Object hubCouponClientRelation (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        if (subRoute.equals("get")) {
            return fnCouponClientRelation_Get(request, connectionString);
        }
        else if (subRoute.equals("get-by-coupon")) {
            return fnCouponClientRelation_GetByCoupon(request, connectionString);
        }
        else if (subRoute.equals("get-by-client")) {
            return fnCouponClientRelation_GetByClient(request, connectionString);
        }
        else if (subRoute.equals("insert")) {
            return fnCouponClientRelation_Insert(request, connectionString);
        }
        else if (subRoute.equals("delete")) {
            return fnCouponClientRelation_Delete(request, connectionString);
        }
        else {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        }
    }

    private static Object fnCouponClientRelation_Get(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCouponClientRelation_Get(?, ?) }");
        spCall.setString(1, request.getQueryParameters().get("coupon-id"));
        spCall.setString(2, request.getQueryParameters().get("client-id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Read the result (if applicable)
        return request.createResponseBuilder(HttpStatus.OK).body(JsonUtilities.resultSetReader(resultSet).toList()).build();
    }
    
    private static Object fnCouponClientRelation_GetByCoupon(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCouponClientRelation_GetByCoupon(?) }");
        spCall.setString(1, request.getQueryParameters().get("coupon-id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Read the result (if applicable)
        return request.createResponseBuilder(HttpStatus.OK).body(JsonUtilities.resultSetReader(resultSet).toList()).build();
    }

    private static Object fnCouponClientRelation_GetByClient(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCouponClientRelation_GetByClient(?) }");
        spCall.setString(1, request.getQueryParameters().get("client-id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Read the result (if applicable)
        return request.createResponseBuilder(HttpStatus.OK).body(JsonUtilities.resultSetReader(resultSet).toList()).build();
    }
    
    private static Object fnCouponClientRelation_Delete(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCouponClientRelation_Delete(?, ?) }");
        spCall.setString(1, request.getQueryParameters().get("coupon-id"));
        spCall.setString(2, request.getQueryParameters().get("client-id"));

        // Execute the procedure
        spCall.executeUpdate();

        // Return success message
        return request.createResponseBuilder(HttpStatus.OK).build();
    }
    
    private static Object fnCouponClientRelation_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spCouponClientRelation_Insert(?, ?) }");
            spCall.setString(1, json.getString("couponId"));
            spCall.setString(2, json.getString("clientId"));
    
            // Execute insert operation
            spCall.executeUpdate();

            // Return success message
            return request.createResponseBuilder(HttpStatus.OK).build();
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }
}
