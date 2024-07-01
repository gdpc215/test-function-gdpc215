package com.function.gdpc215.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.function.gdpc215.model.ProductEntity;
import com.function.gdpc215.model.ProductExtendedEntity;
import com.function.gdpc215.utils.JsonUtilities;

public class ProductsDB {

    public static ProductEntity fnProducts_Get(String connectionString, String id) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spMenuProducts_Get(?) }");
        spCall.setString(1, id);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return ProductEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static List<ProductExtendedEntity> fnProducts_GetByBusiness(String connectionString, String businessId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spMenuProducts_GetByBusiness(?) }");
        spCall.setString(1, businessId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return ProductExtendedEntity.getCollectionFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnProducts_Insert(String connectionString, ProductExtendedEntity entity) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection
                .prepareCall("{ call spMenuProducts_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.productEntity.businessId);
        spCall.setString(2, entity.productEntity.categoryId);
        spCall.setString(3, entity.productEntity.strName);
        spCall.setString(4, entity.productEntity.strDescription);
        spCall.setDouble(5, entity.productEntity.amtPrice);
        spCall.setInt(6, entity.productEntity.amtPreparationTime);
        spCall.setString(7, entity.productEntity.flgDispatchType);
        spCall.setDouble(8, entity.productEntity.amtMinSaleWeight);
        spCall.setString(9, entity.productEntity.strMinSaleWeightMeasure);
        spCall.setBoolean(10, entity.productEntity.flgHasStock);
        spCall.setString(11, entity.productEntity.strImagePath);
        spCall.setString(12, entity.productEntity.strAllergies);
        spCall.setString(13, entity.productEntity.strCaloricInfo);
        spCall.setString(14, entity.strAvailabilityDetails); // ESTO DEBE SALIR DE ACA
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnProducts_Update(String connectionString, ProductExtendedEntity entity) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection
                .prepareCall("{ call spMenuProducts_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.productEntity.id);
        spCall.setString(2, entity.productEntity.categoryId);
        spCall.setString(3, entity.productEntity.strName);
        spCall.setString(4, entity.productEntity.strDescription);
        spCall.setDouble(5, entity.productEntity.amtPrice);
        spCall.setInt(6, entity.productEntity.amtPreparationTime);
        spCall.setString(7, entity.productEntity.flgDispatchType);
        spCall.setDouble(8, entity.productEntity.amtMinSaleWeight);
        spCall.setString(9, entity.productEntity.strMinSaleWeightMeasure);
        spCall.setBoolean(10, entity.productEntity.flgHasStock);
        spCall.setString(11, entity.productEntity.strImagePath);
        spCall.setString(12, entity.productEntity.strAllergies);
        spCall.setString(13, entity.productEntity.strCaloricInfo);
        spCall.setString(14, entity.strAvailabilityDetails); // ESTO DEBE SALIR DE ACA

        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnProducts_Delete(String connectionString, String id) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spMenuProducts_Delete(?) }");
        spCall.setString(1, id);
        // Execute delete operation
        spCall.executeUpdate();
    }

}
