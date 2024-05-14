package com.function.gdpc215.logic;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.model.BillDetailEntity;
import com.function.gdpc215.model.BillDetailExtendedEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class BillDetails {
    
    public static Object hubBillDetails (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnBillDetails_GetById(request, connectionString);
            case "get-extended" -> fnBillDetails_GetExtendedById(request, connectionString);
            case "get-by-bill" -> fnBillDetails_GetByBill(request, connectionString);
            case "get-extended-by-bill" -> fnBillDetails_GetExtendedByBill(request, connectionString);
            case "insert" -> fnBillDetails_Insert(request, connectionString);
            case "update" -> fnBillDetails_Update(request, connectionString);
            case "delete" -> fnBillDetails_Delete(request, connectionString);
            case "clear" -> fnBillDetails_Clear(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnBillDetails_GetById(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_GetById(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        BillDetailEntity entity = BillDetailEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }

    private static Object fnBillDetails_GetExtendedById(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_GetExtendedById(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        BillDetailExtendedEntity entity = BillDetailExtendedEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }

    private static Object fnBillDetails_GetByBill(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_GetByBill(?) }");
        spCall.setString(1, request.getQueryParameters().get("bill-id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        List<BillDetailEntity> entity = BillDetailEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }
    
    private static Object fnBillDetails_GetExtendedByBill(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_GetExtendedByBill(?) }");
        spCall.setString(1, request.getQueryParameters().get("bill-id"));

        // Execute the procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        List<BillDetailExtendedEntity> entity = BillDetailExtendedEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }
    
    private static Object fnBillDetails_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spBillDetails_Insert(?, ?, ?, ?, ?) }");
            spCall.setString(1, json.getString("billId"));
            spCall.setString(2, json.getString("menuItemId"));
            spCall.setBigDecimal(3, new BigDecimal(json.getString("amtListedPrice")));
            spCall.setString(4, json.getString("strSpecialRequirements"));
            spCall.setInt(5, json.getInt("amtAmount"));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBillDetails_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spBillDetails_Update(?, ?, ?, ?) }");
            spCall.setString(1, json.getString("billDetailId"));
            spCall.setBigDecimal(2, new BigDecimal(json.getString("amtListedPrice")));
            spCall.setString(3, json.getString("strSpecialRequirements"));
            spCall.setInt(4, json.getInt("amtAmount"));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBillDetails_Delete(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_Delete(?) }");
        spCall.setString(1, request.getQueryParameters().get("bill-detail-id"));

        // Execute the procedure
        spCall.executeUpdate();

        return null;
    }

    private static Object fnBillDetails_Clear(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_Clear(?) }");
        spCall.setString(1, request.getQueryParameters().get("bill-id"));

        // Execute the procedure
        spCall.executeUpdate();

        return null;
    }
}
