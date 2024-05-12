package com.function.gdpc215.logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import com.function.gdpc215.model.UserEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.function.gdpc215.utils.Utils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class User {
    public static Object hubUser (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        if (subRoute.equals("get")) {
            return fnUser_GetById(request, connectionString);
        }
        else if (subRoute.equals("login-with-credentials")) {
            return fnUser_LoginWithCredentials(request, connectionString);
        }
        else if (subRoute.equals("login-from-socials")) {
            return fnUser_LoginFromSocials(request, connectionString);
        }
        else if (subRoute.equals("create-ghost")) {
            return fnUser_CreateGhost(request, connectionString);
        }
        else if (subRoute.equals("create-from-socials")) {
            return fnUser_CreateFromSocials(request, connectionString);
        } 
        else if (subRoute.equals("update")) {
            return fnUser_Update(request, connectionString);
        } 
        else if (subRoute.equals("deactivate")) {
            return fnUser_Deactivate(request, connectionString);
        }
        else {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        }
    }

    private static Object fnUser_GetById(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        String id = request.getQueryParameters().get("id");

        if (!Utils.validateUUID(id)) { id = ""; }
        if (id == "") {
            return fnUser_CreateGhost(request, connectionString);
        }
        else {
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spUser_Get(?) }");
            spCall.setString(1, id);

            // Call procedure
            ResultSet resultSet = spCall.executeQuery();

            // Cast result to appropiate type
            UserEntity entity = UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
            
            // Returns a ghost user if the id provided doesnt return a valid user 
            return entity != null ? entity : fnUser_CreateGhost(request, connectionString);
        }
    }

    private static Object fnUser_LoginWithCredentials(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spUser_LoginWithCredentials(?, ?) }");
            spCall.setString(1, json.getString("strEmail"));
            spCall.setString(2, json.getString("strPassword"));
    
            // Call procedure
            ResultSet resultSet = spCall.executeQuery();

            // Cast result to appropiate type
            UserEntity entity = UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

            return entity;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnUser_LoginFromSocials(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spUser_LoginFromSocials(?, ?) }");
            spCall.setString(1, json.getString("strEmail"));
            spCall.setString(2, json.getString("strLoginByProvider"));
    
            // Call procedure
            ResultSet resultSet = spCall.executeQuery();

            // Cast to appropiate type
            UserEntity entity = UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
            
            return entity;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnUser_CreateGhost(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spUser_CreateGhost }");

        // Call procedure
        ResultSet resultSet = spCall.executeQuery();

        // Cast result to appropiate type
        UserEntity entity = UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

        return entity;
    }

    private static Object fnUser_CreateFromSocials(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spUser_CreateFromSocials(?, ?, ?, ?) }");
            spCall.setString(1, json.getString("strFirstName"));
            spCall.setString(2, json.getString("strLastName"));
            spCall.setString(3, json.getString("strEmail"));
            spCall.setString(4, json.getString("strLoginByProvider"));
    
            // Call procedure
            ResultSet resultSet = spCall.executeQuery();

            // Cast result to appropiate type
            UserEntity entity = UserEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));

            return entity;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnUser_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spUser_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.getString("id"));
            spCall.setString(2, json.getString("strFirstName"));
            spCall.setString(3, json.getString("strLastName"));
            spCall.setString(4, json.getString("strEmail"));
            spCall.setString(5, json.getString("strPassword"));
            spCall.setString(6, json.getString("strLoginByProvider"));
            spCall.setString(7, json.getString("dateBirth"));
            spCall.setString(8, json.getString("strGender"));
            spCall.setString(9, json.getString("strPhone"));
            spCall.setString(10, json.getString("strLanguagePreferences"));
            spCall.setString(11, json.getString("flgAllowNotifications"));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnUser_Deactivate(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spUser_Deactivate(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Call procedure
        spCall.executeUpdate();

        return null;
    }
}
