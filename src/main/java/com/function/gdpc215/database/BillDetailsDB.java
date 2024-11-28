package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

import com.function.gdpc215.model.BillDetailEntity;
import com.function.gdpc215.model.BillDetailExtendedEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;

public class BillDetailsDB {
    public static BillDetailEntity fnBillDetails_GetById(String connectionString, String billDetailId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_GetById(?) }");
        spCall.setString(1, billDetailId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return BillDetailEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static BillDetailExtendedEntity fnBillDetails_GetExtendedById(String connectionString, String billDetailId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_GetExtendedById(?) }");
        spCall.setString(1, billDetailId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return BillDetailExtendedEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet), false);
    }

    public static List<BillDetailEntity> fnBillDetails_GetByBill(String connectionString, String billId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_GetByBill(?) }");
        spCall.setString(1, billId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return BillDetailEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static List<BillDetailExtendedEntity> fnBillDetails_GetExtendedByBill(String connectionString, String billId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_GetExtendedByBill(?) }");
        spCall.setString(1, billId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        
        // Cast result to appropiate type
        return BillDetailExtendedEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet), true);
    }

    public static void fnBillDetails_Insert(String connectionString, BillDetailEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spBillDetails_Insert(?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.billId);
        spCall.setString(2, entity.menuItemId);
        spCall.setDouble(3, entity.amtListedPrice);
        spCall.setString(4, entity.strSpecialRequirements);
        spCall.setDouble(5, entity.amtAmount);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnBillDetails_InsertByBill(String connectionString, String billId,
            List<BillDetailEntity> entityList) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Create a TVP
        SQLServerDataTable billDetailsTable = new SQLServerDataTable();
        billDetailsTable.addColumnMetadata("menuItemId", Types.VARCHAR);
        billDetailsTable.addColumnMetadata("amtListedPrice", Types.DOUBLE);
        billDetailsTable.addColumnMetadata("amtAmount", Types.DOUBLE);
        billDetailsTable.addColumnMetadata("strSpecialRequirements", Types.VARCHAR);
        // Populate the TVP with data
        for (BillDetailEntity detail : entityList) {
            billDetailsTable.addRow(detail.menuItemId, detail.amtListedPrice, detail.amtAmount,
                    detail.strSpecialRequirements);
        }
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_InsertByBill(?, ?) }");
        spCall.setString(1, billId);
        spCall.setObject(2, billDetailsTable);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnBillDetails_Update(String connectionString, BillDetailEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spBillDetails_Update(?, ?, ?, ?) }");
        spCall.setString(1, entity.billDetailId);
        spCall.setDouble(2, entity.amtListedPrice);
        spCall.setString(3, entity.strSpecialRequirements);
        spCall.setDouble(4, entity.amtAmount);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnBillDetails_Delete(String connectionString, String billDetailId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillDetails_Delete(?) }");
        spCall.setString(1, billDetailId);
        // Execute operation
        spCall.executeUpdate();
    }
}
