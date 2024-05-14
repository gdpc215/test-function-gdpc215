package com.function.gdpc215.logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.model.CategoriesEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Categories {
    
    public static Object hubCategories(String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnCategories_GetById(request, connectionString);
            case "get-by-bid" -> fnCategories_GetByBusiness(request, connectionString);
            case "insert" -> fnCategories_Insert(request, connectionString);
            case "update" -> fnCategories_Update(request, connectionString);
            case "change-order" -> fnCategories_ChangeOrderNumber(request, connectionString);
            case "delete" -> fnCategories_Delete(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }
    
    private static Object fnCategories_GetById(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCategories_Get(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Call procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        CategoriesEntity entity = CategoriesEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }
    
    private static Object fnCategories_GetByBusiness(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCategories_GetByBusiness(?) }");
        spCall.setString(1, request.getQueryParameters().get("business-id"));

        // Call procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        List<CategoriesEntity> entity = CategoriesEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }
    
    private static Object fnCategories_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spCategories_Update(?, ?, ?) }");
            spCall.setString(1, json.optString("id"));
            spCall.setString(2, json.optString("strName"));
            spCall.setString(3, json.optString("strDayAvailability"));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnCategories_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spCategories_Insert(?, ?, ?) }");
            spCall.setString(1, json.optString("businessId"));
            spCall.setString(2, json.optString("strName"));
            spCall.setString(3, json.optString("strDayAvailability"));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnCategories_ChangeOrderNumber(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spCategories_ChangeOrderNumber(?, ?, ?) }");
            spCall.setString(1, json.optString("businessId"));
            spCall.setString(2, json.optString("categoryId"));
            spCall.setString(3, json.optString("newOrderNumber"));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }
    
    private static Object fnCategories_Delete(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCategories_Delete(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute delete operation
        spCall.executeUpdate();

        return null;
    }
}
