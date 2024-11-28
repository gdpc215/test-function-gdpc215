package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;

import com.function.gdpc215.model.ConfigEntity;
import com.function.gdpc215.utils.JsonUtilities;

public class ConfigDB {
    public static ConfigEntity fnConfig_GetByKey(String connectionString, String businessId, String key)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spConfig_GetByKey(?, ?) }");
        spCall.setString(1, businessId);
        spCall.setString(2, key);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return ConfigEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static List<ConfigEntity> fnConfig_GetByBusiness(String connectionString, String businessId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spConfig_GetByBusinessId(?) }");
        spCall.setString(1, businessId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return ConfigEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnConfig_Insert(String connectionString, ConfigEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spConfig_Insert(?, ?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.businessId);
        spCall.setString(3, entity.strValue);
        spCall.setString(4, entity.strDescription);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnConfig_Update(String connectionString, ConfigEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spConfig_Update(?, ?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.businessId);
        spCall.setString(3, entity.strValue);
        spCall.setString(4, entity.strDescription);
        // Execute operation
        spCall.executeUpdate();
    }

}
