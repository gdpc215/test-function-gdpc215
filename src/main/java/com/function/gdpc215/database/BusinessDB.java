package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.function.gdpc215.model.BusinessEntity;
import com.function.gdpc215.utils.JsonUtilities;

public class BusinessDB {
    public static BusinessEntity fnBusiness_GetById(String connectionString, String businessId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        PreparedStatement spCall = connection.prepareCall("{ call spBusiness_Get(?) }");
        spCall.setString(1, businessId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return BusinessEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static BusinessEntity fnBusiness_GetBySubdomain(String connectionString, String subdomain)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        PreparedStatement spCall = connection.prepareCall("{ call spBusiness_GetBySubDomain(?) }");
        spCall.setString(1, subdomain);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return BusinessEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnBusiness_Insert(String connectionString, BusinessEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        CallableStatement spCall = connection.prepareCall("{ call spBusiness_Create(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.strSubDomain);
        spCall.setString(2, entity.strName);
        spCall.setString(3, entity.strDescription);
        spCall.setString(4, entity.strSocials);
        spCall.setString(5, entity.strWebPage);
        spCall.setString(6, entity.strSegment);
        spCall.setString(7, entity.strSpecialties);
        spCall.setString(8, entity.strAddress);
        spCall.setString(9, entity.strContact);
        spCall.setString(10, entity.strMainPageImageUrl);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnBusiness_Update(String connectionString, BusinessEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        CallableStatement spCall = connection.prepareCall("{ call spBusiness_Update(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.strName);
        spCall.setString(3, entity.strDescription);
        spCall.setString(4, entity.strSocials);
        spCall.setString(5, entity.strWebPage);
        spCall.setString(6, entity.strSegment);
        spCall.setString(7, entity.strSpecialties);
        spCall.setString(8, entity.strAddress);
        spCall.setString(9, entity.strContact);
        spCall.setString(10, entity.strMainPageImageUrl);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnBusiness_UpdateSubDomain(String connectionString, String businessId, String subdomain)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        CallableStatement spCall = connection.prepareCall("{ call spBusiness_UpdateSubDomain(?, ?) }");
        spCall.setString(1, businessId);
        spCall.setString(2, subdomain);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnBusiness_Deactivate(String connectionString, String businessId)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement and set parameters
        PreparedStatement spCall = connection.prepareCall("{ call spBusiness_Deactivate(?) }");
        spCall.setString(1, businessId);
        // Execute operation
        spCall.executeUpdate();
    }
}
