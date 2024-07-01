package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.function.gdpc215.model.TableEntity;
import com.function.gdpc215.utils.JsonUtilities;

public class TableDB {

    public static TableEntity fnTable_GetById(String connectionString, String id) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spTable_Get(?) }");
        spCall.setString(1, id);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return TableEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static List<TableEntity> fnTable_GetByBusiness(String connectionString, String businessId) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spTable_GetByBusinessId(?) }");
        spCall.setString(1, businessId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return TableEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static TableEntity fnTable_ValidateTable(String connectionString, String businessId, String tableNumber)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spTable_ValidateTable(?, ?) }");
        spCall.setString(1, businessId);
        spCall.setString(2, tableNumber);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return TableEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnTable_Insert(String connectionString, TableEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spTable_Insert(?, ?) }");
        spCall.setString(1, entity.businessId);
        spCall.setString(2, entity.tableNumber);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnTable_Update(String connectionString, TableEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spTable_Update(?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.tableNumber);
        spCall.setBoolean(3, entity.flgActive);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnTable_UpdateActiveUsers(String connectionString, String tableId, Boolean flgAction)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spTable_UpdateActiveUsers(?, ?, ?) }");
        spCall.setString(1, tableId);
        spCall.setBoolean(2, flgAction);
        // Execute operation
        spCall.executeUpdate();
    }
}
