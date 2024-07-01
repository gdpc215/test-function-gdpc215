package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.function.gdpc215.model.AvailabilityEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.Utils;

public class AvailabilityDB {
    public static AvailabilityEntity fnAvailability_GetById(String connectionString, String availabilityId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spAvailability_Get(?) }");
        spCall.setString(1, availabilityId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return AvailabilityEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static List<AvailabilityEntity> fnAvailability_GetByBusinessId(String connectionString, String businessId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spAvailability_GetByBusinessId(?) }");
        spCall.setString(1, businessId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return AvailabilityEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnAvailability_Insert(String connectionString, AvailabilityEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection
                .prepareCall("{ call spAvailability_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.businessId);
        spCall.setString(2, entity.strName);
        spCall.setString(3, entity.strDescription);
        spCall.setBoolean(4, entity.flgShowDescription);
        spCall.setString(5, entity.strAvailableDays);
        spCall.setTime(6, entity.timeAvailabilityStart);
        spCall.setTime(7, entity.timeAvailabilityEnd);
        spCall.setDate(8, Utils.dateConverterToSql(entity.dateValidityStart));
        spCall.setDate(9, Utils.dateConverterToSql(entity.dateValidityEnd));
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnAvailability_InsertArrayByProduct(String connectionString, String productId,
            List<AvailabilityEntity> availabilityList) throws Exception {
        // Preparing objects
        List<String> availiabilityIdString = new ArrayList<>(); 
        availabilityList.forEach(entity -> {
            availiabilityIdString.add(entity.id);
        });
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection
                .prepareCall("{ call spAvailabilityProductRelation_InsertArray(?, ?) }");
        
        spCall.setString(1, availiabilityIdString.toString()); // COLLECT HERE!!!!!!!
        spCall.setString(2, productId);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnAvailability_Update(String connectionString, AvailabilityEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection
                .prepareCall("{ call spAvailability_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.strName);
        spCall.setString(3, entity.strDescription);
        spCall.setBoolean(4, entity.flgShowDescription);
        spCall.setBoolean(5, entity.flgActive);
        spCall.setString(6, entity.strAvailableDays);
        spCall.setTime(7, entity.timeAvailabilityStart);
        spCall.setTime(8, entity.timeAvailabilityEnd);
        spCall.setDate(9, Utils.dateConverterToSql(entity.dateValidityStart));
        spCall.setDate(10, Utils.dateConverterToSql(entity.dateValidityEnd));
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnAvailability_Delete(String connectionString, String availabilityId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spAvailability_Delete(?) }");
        spCall.setString(1, availabilityId);
        // Execute operation
        spCall.executeUpdate();
    }
}
