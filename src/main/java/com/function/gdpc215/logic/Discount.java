package com.function.gdpc215.logic;

import java.math.BigDecimal;
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

import com.function.gdpc215.model.DiscountEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Discount {
    
    public static Object hubDiscount (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        if (subRoute.equals("get")) {
            return fnDiscount_Get(request, connectionString);
        }
        else if (subRoute.equals("get-by-bid")) {
            return fnDiscount_GetByBusinessId(request, connectionString);
        }
        else if (subRoute.equals("insert")) {
            return fnDiscount_Insert(request, connectionString);
        }
        else if (subRoute.equals("update")) {
            return fnDiscount_Update(request, connectionString);
        }
        else if (subRoute.equals("delete")) {
            return fnDiscount_Delete(request, connectionString);
        }
        else {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        }
    }
    
    private static Object fnDiscount_Get(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spDiscount_Get(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        DiscountEntity entity = DiscountEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }
    
    private static Object fnDiscount_GetByBusinessId(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spDiscount_GetByBusiness(?) }");
        spCall.setString(1, request.getQueryParameters().get("business-id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        List<DiscountEntity> entity = DiscountEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }

    private static Object fnDiscount_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spDiscount_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.getString("businessId"));
            spCall.setString(2, json.getString("strName"));
            spCall.setString(3, json.getString("strDescription"));
            spCall.setBigDecimal(4, new BigDecimal(json.getString("amtDiscount")));
            spCall.setString(5, json.getString("strDiscountType"));
            spCall.setBoolean(6, json.getBoolean("flgActive"));
            spCall.setString(7, json.getString("strAvailableDays"));
            spCall.setTime(8, Time.valueOf(json.getString("timeAvailabilityStart")));
            spCall.setTime(9, Time.valueOf(json.getString("timeAvailabilityEnd")));
            spCall.setDate(10, Date.valueOf(json.getString("dateValidityStart")));
            spCall.setDate(11, Date.valueOf(json.getString("dateValidityEnd")));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnDiscount_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spDiscount_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.getString("id"));
            spCall.setString(2, json.getString("businessId"));
            spCall.setString(3, json.getString("strName"));
            spCall.setString(4, json.getString("strDescription"));
            spCall.setBigDecimal(5, new BigDecimal(json.getString("amtDiscount")));
            spCall.setString(6, json.getString("strDiscountType"));
            spCall.setBoolean(7, json.getBoolean("flgActive"));
            spCall.setString(8, json.getString("strAvailableDays"));
            spCall.setTime(9, Time.valueOf(json.getString("timeAvailabilityStart")));
            spCall.setTime(10, Time.valueOf(json.getString("timeAvailabilityEnd")));
            spCall.setDate(11, Date.valueOf(json.getString("dateValidityStart")));
            spCall.setDate(12, Date.valueOf(json.getString("dateValidityEnd")));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnDiscount_Delete(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spDiscount_Delete(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute the procedure
        spCall.executeUpdate();

        return null;
    }
}

