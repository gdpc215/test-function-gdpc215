package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.function.gdpc215.model.UserEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.Utils;

public class UserDB {

    public static UserEntity fnUser_GetById(String connectionString, String id)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spUser_Get(?) }");
        spCall.setString(1, id);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static UserEntity fnUser_LoginAttemptWithEmail(String connectionString, String email)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spUser_LoginAttemptWithEmail(?) }");
        spCall.setString(1, email);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static UserEntity fnUser_LoginAttemptWithSocials(String connectionString, String strEmail,
            String strLoginByProvider) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spUser_LoginAttemptWithSocials(?, ?) }");
        spCall.setString(1, strEmail);
        spCall.setString(2, strLoginByProvider);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static UserEntity fnUser_CreateGhost(String connectionString)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spUser_CreateGhost }");
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static UserEntity fnUser_CreateFromSocials(String connectionString, UserEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spUser_CreateFromSocials(?, ?, ?, ?) }");
        spCall.setString(1, entity.strFirstName);
        spCall.setString(2, entity.strLastName);
        spCall.setString(3, entity.strEmail);
        spCall.setString(4, entity.strLoginByProvider);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnUser_UpdateInfo(String connectionString, UserEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection
                .prepareCall("{ call spUser_UpdateInformation(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.strFirstName);
        spCall.setString(3, entity.strLastName);
        spCall.setString(4, entity.strEmail);
        spCall.setDate(5, Utils.dateConverterToSql(entity.dateBirth));
        spCall.setString(6, entity.strGender);
        spCall.setString(7, entity.strPhone);
        spCall.setString(8, entity.strLanguagePreferences);
        spCall.setBoolean(9, entity.flgAllowNotifications);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnUser_UpdateCredentials(String connectionString, UserEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spUser_UpdateCredentials(?, ?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.strEmail);
        spCall.setString(3, entity.strPassword);
        spCall.setString(4, entity.strPasswordSalt);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnUser_Deactivate(String connectionString, String userId) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spUser_Deactivate(?) }");
        spCall.setString(1, userId);
        // Execute operation
        spCall.executeUpdate();
    }
}
