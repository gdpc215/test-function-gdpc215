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

import com.function.gdpc215.model.TableEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.function.gdpc215.utils.Utils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Table {
    
    public static Object hubTable(String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnTable_GetById(request, connectionString);
            case "get-by-bid" -> fnTable_GetByBusiness(request, connectionString);
            case "validate-table" -> fnTable_ValidateTable(request, connectionString);
            case "insert" -> fnTable_Insert(request, connectionString);
            case "update" -> fnTable_Update(request, connectionString);
            case "update-active-users" -> fnTable_UpdateActiveUsers(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnTable_GetById(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        String id = request.getQueryParameters().get("id");
        
        if (id.equals("")) {
            return new Exception("ID can't be empty");
        }
        else if (!Utils.validateUUID(id)) {
            return new Exception("ID is not a valid UUID");
        }
        else {
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spTable_Get(?) }");
            spCall.setString(1, request.getQueryParameters().get("id"));
    
            // Execute the procedure
            ResultSet resultSet = spCall.executeQuery();
            
            // Read result
            TableEntity entity = TableEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    
            return entity != null ? entity : new Exception("Invalid table number");
        }
    }

    private static Object fnTable_GetByBusiness(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spTable_GetByBusinessId(?) }");
        spCall.setString(1, request.getQueryParameters().get("business-id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();
        
        // Read result
        List<TableEntity> entity = TableEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }

    private static Object fnTable_ValidateTable(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        String businessId = request.getQueryParameters().get("business-id");
        String tableNumber = request.getQueryParameters().get("table-number");
        
        if (businessId.equals("") || tableNumber.equals("") ) {
            return new Exception("Parameters can't be empty");
        }
        else {
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spTable_ValidateTable(?, ?) }");
            spCall.setString(1, businessId);
            spCall.setString(2, tableNumber);
    
            // Execute the procedure
            ResultSet resultSet = spCall.executeQuery();
            
            // Read result
            TableEntity entity = TableEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    
            return !entity.id.equals("") ? entity : new Exception("Invalid table number");
        }
    }

    private static Object fnTable_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spTable_Insert(?, ?) }");
            spCall.setString(1, json.optString("businessId"));
            spCall.setString(2, json.optString("tableNumber"));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnTable_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spTable_Update(?, ?, ?) }");
            spCall.setString(1, json.optString("id"));
            spCall.setString(2, json.optString("tableNumber"));
            spCall.setBoolean(3, json.optBoolean("flgActive"));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }
    
    private static Object fnTable_UpdateActiveUsers(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spTable_UpdateActiveUsers(?, ?, ?) }");
            spCall.setString(1, json.optString("id"));
            spCall.setBoolean(2, json.optBoolean("flgAction"));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }
}
