package com.function.gdpc215.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.function.gdpc215.model.BillStateEntity;
import com.function.gdpc215.utils.JsonUtilities;

public class BillStateDB {
    public static List<BillStateEntity> fnBillStatus_Get(String connectionString)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBillState_Get }");
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return BillStateEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }
}
