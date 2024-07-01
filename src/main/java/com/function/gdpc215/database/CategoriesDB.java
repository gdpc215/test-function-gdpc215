package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.function.gdpc215.model.CategoriesEntity;
import com.function.gdpc215.utils.JsonUtilities;

public class CategoriesDB {

    public static CategoriesEntity fnCategories_GetById(String connectionString, String categoryId) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCategories_Get(?) }");
        spCall.setString(1, categoryId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return CategoriesEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static List<CategoriesEntity> fnCategories_GetByBusiness(String connectionString, String businessId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCategories_GetByBusiness(?) }");
        spCall.setString(1, businessId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return CategoriesEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnCategories_Update(String connectionString, CategoriesEntity entity) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spCategories_Update(?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.strName);
        spCall.setString(3, entity.strDayAvailability);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnCategories_Insert(String connectionString, CategoriesEntity entity) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spCategories_Insert(?, ?, ?) }");
        spCall.setString(1, entity.businessId);
        spCall.setString(2, entity.strName);
        spCall.setString(3, entity.strDayAvailability);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnCategories_ChangeOrderNumber(String connectionString, String categoryId, int newOrderNumber) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spCategories_ChangeOrderNumber(?, ?) }");
        spCall.setString(1, categoryId);
        spCall.setInt(2, newOrderNumber);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnCategories_Delete(String connectionString, String id) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCategories_Delete(?) }");
        spCall.setString(1, id);
        // Execute delete operation
        spCall.executeUpdate();
    }
}
