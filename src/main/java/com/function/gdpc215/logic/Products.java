package com.function.gdpc215.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.model.ProductEntity;
import com.function.gdpc215.model.ProductExtendedEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Products {

    public static Object hubProducts (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnProducts_Get(request, connectionString);
            case "get-by-business" -> fnProducts_GetViewByBusiness(request, connectionString);
            case "delete" -> fnProducts_Delete(request, connectionString);
            case "insert" -> fnProducts_Insert(request, connectionString);
            case "update" -> fnProducts_Update(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }
    
    private static Object fnProducts_Get(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spMenuProducts_Get(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute operation
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        ProductEntity entity = ProductEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }
 
    private static Object fnProducts_GetViewByBusiness(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spMenuProducts_GetByBusiness(?) }");
        spCall.setString(1, request.getQueryParameters().get("business-id"));

        // Execute operation
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        List<ProductExtendedEntity> entity = ProductExtendedEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }

    private static Object fnProducts_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spMenuProducts_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.optString("businessId"));
            spCall.setString(2, json.optString("categoryId"));
            spCall.setString(3, json.optString("strName"));
            spCall.setString(4, json.optString("strDescription"));
            spCall.setDouble(5, json.optDouble("amtPrice"));
            spCall.setInt(6, json.optInt("amtPreparationTime"));
            spCall.setString(7, json.optString("flgDispatchType"));
            spCall.setDouble(8, json.optDouble("amtMinSaleWeight"));
            spCall.setString(9, json.optString("strMinSaleWeightMeasure"));
            spCall.setBoolean(10, json.optBoolean("flgHasStock"));
            spCall.setString(11, json.optString("strImagePath"));
            spCall.setString(12, json.optString("strAllergies"));
            spCall.setString(13, json.optString("strCaloricInfo"));
            spCall.setString(14, json.optString("strAvailabilityArray"));
    
            // Execute operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnProducts_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spMenuProducts_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.optString("id"));
            spCall.setString(2, json.optString("categoryId"));
            spCall.setString(3, json.optString("strName"));
            spCall.setString(4, json.optString("strDescription"));
            spCall.setDouble(5, Double.parseDouble(json.optString("amtPrice")));
            spCall.setInt(6, Integer.parseInt(json.optString("amtPreparationTime")));
            spCall.setString(7, json.optString("flgDispatchType"));
            spCall.setDouble(8, Double.parseDouble(json.optString("amtMinSaleWeight")));
            spCall.setString(9, json.optString("strMinSaleWeightMeasure"));
            spCall.setBoolean(10, Boolean.parseBoolean(json.optString("flgHasStock")));
            spCall.setString(11, json.optString("strImagePath"));
            spCall.setString(12, json.optString("strAllergies"));
            spCall.setString(13, json.optString("strCaloricInfo"));
            spCall.setString(14, json.optString("strAvailabilityArray"));

            // Execute operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnProducts_Delete(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spMenuProducts_Delete(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute delete operation
        spCall.executeUpdate();

        return null;
    }
    
} 
