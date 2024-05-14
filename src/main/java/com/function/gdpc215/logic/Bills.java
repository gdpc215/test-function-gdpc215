package com.function.gdpc215.logic;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.model.BillDetailEntity;
import com.function.gdpc215.model.BillDetailExtendedEntity;
import com.function.gdpc215.model.BillEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Bills {
    public static Object hubBills (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnBills_GetById(request, connectionString);
            case "get-by-bid" -> fnBills_GetByBusinessId(request, connectionString);
            case "update" -> fnBills_UpdateBill(request, connectionString);
            case "insert" -> fnBills_InsertBill(request, connectionString);
            case "init" -> fnBills_InitBill(request, connectionString);
            case "calculate-tab" -> fnBills_CalculateTabAmount(request, connectionString);
            case "apply-coupon" -> fnBills_ApplyCoupon(request, connectionString);
            case "add-tip" -> fnBills_AddTip(request, connectionString);
            case "close" -> fnBills_CloseBill(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }
    
    private static Object fnBills_GetById(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        String billId = request.getQueryParameters().get("id");

        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBills_GetById(?) }");
        spCall.setString(1, billId);
        // Call procedure
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        BillEntity entity = BillEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        // Prepare statement for bill details
        spCall.clearParameters();
        spCall = connection.prepareCall("{ call spBillDetails_GetExtendedByBill(?) }");
        spCall.setString(1, billId);
        // Execute the procedure
        resultSet = spCall.executeQuery();
        // Read the result and merge
        entity.billDetails = BillDetailExtendedEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
        
        return entity;
    }

    private static Object fnBills_GetByBusinessId(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBills_GetByBusinessId(?) }");
        spCall.setString(1, request.getQueryParameters().get("business-id"));

        // Call procedure
        ResultSet resultSet = spCall.executeQuery();
        
        // Cast result to appropiate type
        List<BillEntity> entity = BillEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
        
        return entity;
    }

    private static Object fnBills_UpdateBill(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            String billId = json.getString("billId");
            
            // Prepare statement and set parameters
            PreparedStatement spCall = connection.prepareCall("{ call spBills_UpdateBill(?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, billId);
            spCall.setInt(4, json.getInt("tableId"));
            spCall.setInt(5, json.getInt("billState"));
            spCall.setString(6, json.getString("couponId"));
            spCall.setDouble(7, json.getDouble("amtTotalTab"));
            spCall.setDouble(8, json.getDouble("amtTip"));
            spCall.setDouble(9, json.getDouble("amtTotalChargeable"));
    
            // Execute update operation
            spCall.executeUpdate();

            /* Inserting bill details */
            List<BillDetailEntity> billDetailEntityList = BillDetailExtendedEntity.getBaseObjCollectionFromJsonArray(json.getJSONArray("billDetails"));

            // Initialize structure to insert an array of bill details
            Struct structuredType = connection.createStruct("TYPE_BILL_DETAILS", billDetailEntityList.toArray());
            spCall.clearParameters();
            spCall = connection.prepareCall("{ call spBillDetails_InsertByBill(?, ?) }");
            spCall.setString(1, billId);
            spCall.setObject(2, structuredType);

            // Call procedure
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_InsertBill(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Prepare statement and set parameters
            PreparedStatement spCall = connection.prepareCall("{ call spBills_InsertBill(?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.getString("businessId"));
            spCall.setString(2, json.getString("userId"));
            spCall.setInt(3, json.getInt("tableId"));
            spCall.setInt(4, json.getInt("billState"));
            spCall.setString(5, json.getString("couponId"));
            spCall.setDouble(6, json.getDouble("amtTotalTab"));
            spCall.setDouble(7, json.getDouble("amtTip"));
            spCall.setDouble(8, json.getDouble("amtTotalChargeable"));
    
            // Execute insert operation
            ResultSet resultSet = spCall.executeQuery();

            // Cast result to appropiate type
            BillEntity entity = BillEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

            /* Inserting bill details */
            if(entity != null) {
                List<BillDetailEntity> billDetailEntityList = BillDetailExtendedEntity.getBaseObjCollectionFromJsonArray(json.getJSONArray("billDetails"));

                // Initialize structure to insert an array of bill details
                Struct structuredType = connection.createStruct("TYPE_BILL_DETAILS", billDetailEntityList.toArray());
                spCall.clearParameters();
                spCall = connection.prepareCall("{ call spBillDetails_InsertByBill(?, ?) }");
                spCall.setString(1, entity.billId);
                spCall.setObject(2, structuredType);

                // Call procedure
                spCall.executeUpdate();

                return null;
            }
            else {
                throw new SQLException("Error al insertar registro tipo Bill");
            }
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_InitBill(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spBills_InitBill(?, ?, ?) }");
            spCall.setString(1, json.getString("businessId"));
            spCall.setInt(2, json.getInt("tableId"));
            spCall.setString(3, json.getString("clientId"));
    
            // Execute operation
            ResultSet resultSet = spCall.executeQuery();

            // Cast result to appropiate type
            BillEntity entity = BillEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

            return entity;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_CalculateTabAmount(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement 
        PreparedStatement spCall = connection.prepareCall("{ call spBills_CalculateTabAmount(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Call procedure
        spCall.executeUpdate();

        return null;
    }
    
    private static Object fnBills_ApplyCoupon(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spBills_ApplyCoupon(?, ?, ?) }");
            spCall.setString(1, json.getString("businessId"));
            spCall.setString(2, json.getString("id"));
            spCall.setString(3, json.getString("couponCode"));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_AddTip(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spBills_AddTip(?, ?) }");
            spCall.setString(1, json.getString("id"));
            spCall.setBigDecimal(2, new BigDecimal(json.getString("amtTip")));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_CloseBill(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement 
        PreparedStatement spCall = connection.prepareCall("{ call spBills_CloseBill(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Call procedure
        spCall.executeUpdate();

        return null;
    }
    
}
