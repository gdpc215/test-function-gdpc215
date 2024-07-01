package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.function.gdpc215.model.BillEntity;
import com.function.gdpc215.utils.JsonUtilities;

public class BillsDB {
    public static BillEntity fnBills_GetById(String connectionString, String billId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBills_GetById(?) }");
        spCall.setString(1, billId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return BillEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static List<BillEntity> fnBills_GetByBusinessId(String connectionString, String businessId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBills_GetByBusinessId(?) }");
        spCall.setString(1, businessId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return BillEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnBills_UpdateBill(String connectionString, BillEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        PreparedStatement spCall = connection.prepareCall("{ call spBills_UpdateBill(?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.billId);
        spCall.setString(2, entity.tableId);
        spCall.setBoolean(3, entity.billState);
        spCall.setString(4, entity.couponId);
        spCall.setDouble(5, entity.amtTotalTab);
        spCall.setDouble(6, entity.amtTip);
        spCall.setDouble(7, entity.amtTotalChargeable);
        // Execute operation
        spCall.executeUpdate();
    }

    public static BillEntity fnBills_InitBill(String connectionString, String businessId, String tableId, String userId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        CallableStatement spCall = connection.prepareCall("{ call spBills_InitBill(?, ?, ?) }");
        spCall.setString(1, businessId);
        spCall.setString(2, tableId);
        spCall.setString(3, userId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return BillEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnBills_CalculateTabAmount(String connectionString, String billId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        PreparedStatement spCall = connection.prepareCall("{ call spBills_CalculateTabAmount(?) }");
        spCall.setString(1, billId);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnBills_ApplyCoupon(String connectionString, String businessId, String billID,
            String couponCode)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        CallableStatement spCall = connection.prepareCall("{ call spBills_ApplyCoupon(?, ?, ?) }");
        spCall.setString(1, businessId);
        spCall.setString(2, billID);
        spCall.setString(3, couponCode);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnBills_AddTip(String connectionString, String billId, double amtTip)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        CallableStatement spCall = connection.prepareCall("{ call spBills_AddTip(?, ?) }");
        spCall.setString(1, billId);
        spCall.setDouble(2, amtTip);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnBills_CloseBill(String connectionString, String billId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        PreparedStatement spCall = connection.prepareCall("{ call spBills_CloseBill(?) }");
        spCall.setString(1, billId);
        // Execute operation
        spCall.executeUpdate();
    }
}
