package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.function.gdpc215.model.DiscountEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.Utils;

public class DiscountDB {

    public static DiscountEntity fnDiscount_Get(String connectionString, String id) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spDiscount_Get(?) }");
        spCall.setString(1, id);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return DiscountEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static List<DiscountEntity> fnDiscount_GetByBusinessId(String connectionString, String businessId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spDiscount_GetByBusiness(?) }");
        spCall.setString(1, businessId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return DiscountEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnDiscount_Insert(String connectionString, DiscountEntity entity) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection
                .prepareCall("{ call spDiscount_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.businessId);
        spCall.setString(2, entity.strName);
        spCall.setString(3, entity.strDescription);
        spCall.setDouble(4, entity.amtDiscount);
        spCall.setString(5, entity.strDiscountType);
        spCall.setBoolean(6, entity.flgActive);
        spCall.setString(7, entity.strAvailableDays);
        spCall.setTime(8, entity.timeAvailabilityStart);
        spCall.setTime(9, entity.timeAvailabilityEnd);
        spCall.setDate(10, Utils.dateConverterToSql(entity.dateValidityStart));
        spCall.setDate(11, Utils.dateConverterToSql(entity.dateValidityEnd));
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnDiscount_Update(String connectionString, DiscountEntity entity) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection
                .prepareCall("{ call spDiscount_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.strName);
        spCall.setString(3, entity.strDescription);
        spCall.setDouble(4, entity.amtDiscount);
        spCall.setString(5, entity.strDiscountType);
        spCall.setBoolean(6, entity.flgActive);
        spCall.setString(7, entity.strAvailableDays);
        spCall.setTime(8, entity.timeAvailabilityStart);
        spCall.setTime(9, entity.timeAvailabilityEnd);
        spCall.setDate(11, Utils.dateConverterToSql(entity.dateValidityStart));
        spCall.setDate(12, Utils.dateConverterToSql(entity.dateValidityEnd));
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnDiscount_Delete(String connectionString, String id) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spDiscount_Delete(?) }");
        spCall.setString(1, id);
        // Execute operation
        spCall.executeUpdate();
    }
}
