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

public class DiscountProductRelation {
    
    public static Object hubDiscountProductRelation (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        if (subRoute.equals("get")) {
            return fnDiscountProductRelation_GetByDiscount(request, connectionString);
        }
        else if (subRoute.equals("insert")) {
            return fnDiscountProductRelation_Insert(request, connectionString);
        }
        else if (subRoute.equals("insert-product-array")) {
            return fnDiscountProductRelation_InsertProductArray(request, connectionString);
        }
        else if (subRoute.equals("delete")) {
            return fnDiscountProductRelation_Delete(request, connectionString);
        }
        else if (subRoute.equals("delete-by-discount")) {
            return fnDiscountProductRelation_DeleteByDiscountId(request, connectionString);
        }
        else {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        }
    }

    private static Object fnDiscountProductRelation_GetByDiscount(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spDiscountProductRelation_GetByDiscount(?) }");
        spCall.setString(1, request.getQueryParameters().get("discount-id"));

        // Call procedure
        ResultSet resultSet = spCall.executeQuery();

        // Read result (if applicable)
        return request.createResponseBuilder(HttpStatus.OK).body(JsonUtilities.resultSetReader(resultSet).toList()).build();
    }
    
    private static Object fnDiscountProductRelation_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spDiscountProductRelation_Insert(?, ?) }");
            spCall.setString(1, json.optString("discountId"));
            spCall.setString(2, json.optString("productId"));
    
            // Execute insert operation
            spCall.executeUpdate();

            // Return success message
            return request.createResponseBuilder(HttpStatus.OK).build();
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnDiscountProductRelation_InsertProductArray(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spDiscountProductRelation_InsertProductArray(?, ?) }");
            spCall.setString(1, json.optString("discountId"));
            spCall.setString(2, json.optString("productIdArray"));
    
            // Execute insert operation
            spCall.executeUpdate();

            // Return success message
            return request.createResponseBuilder(HttpStatus.OK).build();
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnDiscountProductRelation_Delete(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spDiscountProductRelation_Delete(?, ?) }");
            spCall.setString(1, json.optString("discountId"));
            spCall.setString(2, json.optString("productId"));
    
            // Execute the procedure
            spCall.executeUpdate();
    
            // Return success message
            return request.createResponseBuilder(HttpStatus.OK).build();
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnDiscountProductRelation_DeleteByDiscountId(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spDiscountProductRelation_DeleteByDiscountId(?) }");
            spCall.setString(1, json.optString("discountId"));
    
            // Execute the procedure
            spCall.executeUpdate();
    
            // Return success message
            return request.createResponseBuilder(HttpStatus.OK).build();
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

}
