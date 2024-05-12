package com.function.gdpc215.logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.model.AvailabilityEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Availability {
    public static Object hubAvailability (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        if (subRoute.equals("get")) {
            return fnAvailability_GetById(request, connectionString);
        }
        else if (subRoute.equals("get-by-bid")) {
            return fnAvailability_GetByBusinessId(request, connectionString);
        }
        else if (subRoute.equals("insert")) {
            return fnAvailability_Insert(request, connectionString);
        }
        else if (subRoute.equals("update")) {
            return fnAvailability_Update(request, connectionString);
        }
        else if (subRoute.equals("delete")) {
            return fnAvailability_Delete(request, connectionString);
        }
        else {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        }
    }

    private static Object fnAvailability_GetById(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        String availabilityId = request.getQueryParameters().get("id"); 

        // Prepare statement for availability
        PreparedStatement spCall = connection.prepareCall("{ call spAvailability_Get(?) }");
        spCall.setString(1, availabilityId);
        // Call procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        AvailabilityEntity entity = AvailabilityEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }

    private static Object fnAvailability_GetByBusinessId(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spAvailability_GetByBusinessId(?) }");
        spCall.setString(1, request.getQueryParameters().get("business-id"));

        // Call procedure
        ResultSet resultSet = spCall.executeQuery();
        
        // Cast result to appropiate type
        List<AvailabilityEntity> entity = AvailabilityEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
        
        return entity;
    }

    private static Object fnAvailability_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spAvailability_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.getString("business-id"));
            spCall.setString(2, json.getString("strName"));
            spCall.setString(3, json.getString("strDescription"));
            spCall.setBoolean(4, json.getBoolean("flgShowDescription"));
            spCall.setString(5, json.getString("strAvailableDays"));
            spCall.setTime(6, Time.valueOf(json.getString("timeAvailabilityStart")));
            spCall.setTime(7, Time.valueOf(json.getString("timeAvailabilityEnd")));
            spCall.setDate(8, Date.valueOf(json.getString("dateValidityStart")));
            spCall.setDate(9, Date.valueOf(json.getString("dateValidityEnd")));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnAvailability_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spAvailability_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.getString("id"));
            spCall.setString(2, json.getString("strName"));
            spCall.setString(3, json.getString("strDescription"));
            spCall.setBoolean(4, json.getBoolean("flgShowDescription"));
            spCall.setBoolean(5, json.getBoolean("flgActive"));
            spCall.setString(6, json.getString("strAvailableDays"));
            spCall.setTime(7, Time.valueOf(json.getString("timeAvailabilityStart")));
            spCall.setTime(8, Time.valueOf(json.getString("timeAvailabilityEnd")));
            spCall.setDate(9, Date.valueOf(json.getString("dateValidityStart")));
            spCall.setDate(10, Date.valueOf(json.getString("dateValidityEnd")));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnAvailability_Delete(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spAvailability_Delete(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Call procedure
        spCall.executeUpdate();

        return null;
    }

}
