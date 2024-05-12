package com.function.gdpc215.logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.model.BusinessEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.function.gdpc215.utils.Utils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Business {
    
    public static Object hubBusiness(String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        if (subRoute.equals("get")) {            
            return fnBusiness_GetById(request, connectionString);
        }
        else if (subRoute.equals("get-by-subdomain")) {            
            return fnBusiness_GetBySubdomain(request, connectionString);
        }
        else if (subRoute.equals("insert")) {
            return fnBusiness_Insert(request, connectionString);
        }
        else if (subRoute.equals("update")) {
            return fnBusiness_Update(request, connectionString);
        }
        else if (subRoute.equals("update-subdomain")) {
            return fnBusiness_UpdateSubDomain(request, connectionString);
        }
        else if (subRoute.equals("deactivate")) {
            return fnBusiness_Deactivate(request, connectionString);
        }
        else {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        }
    }

    private static Object fnBusiness_GetById(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        String id = request.getQueryParameters().get("id");

        if (id == "") {
            return new Exception("ID can't be empty");
        }
        else if (!Utils.validateUUID(id)) {
            return new Exception("ID is not a valid UUID");
        }
        else {
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spBusiness_Get(?) }");
            spCall.setString(1, id);
    
            // Execute the procedure
            ResultSet resultSet = spCall.executeQuery();
            
            // Read result
            BusinessEntity entity = BusinessEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
            
            return entity.id != "" ? entity : new Exception("Invalid subdomain");
        }
    }

    private static Object fnBusiness_GetBySubdomain(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        String subdomain = request.getQueryParameters().get("subdomain");
        
        if (subdomain == "") {
            return new BusinessEntity();
        }
        else {
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spBusiness_GetBySubDomain(?) }");
            spCall.setString(1, subdomain);

            // Execute the procedure
            ResultSet resultSet = spCall.executeQuery();
            
            // Read result
            BusinessEntity entity = BusinessEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
            
            return entity.id != "" ? entity : new Exception("Subdominio invalido");
        }
    }

    private static Object fnBusiness_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spBusiness_Create(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.getString("strSubDomain"));
            spCall.setString(2, json.getString("strName"));
            spCall.setString(3, json.getString("strDescription"));
            spCall.setString(4, json.getString("strSocials"));
            spCall.setString(5, json.getString("strWebPage"));
            spCall.setString(6, json.getString("strSegment"));
            spCall.setString(7, json.getString("strSpecialties"));
            spCall.setString(8, json.getString("strAddress"));
            spCall.setString(9, json.getString("strContact"));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBusiness_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spBusiness_Update(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            spCall.setString(1, json.getString("id"));
            spCall.setString(2, json.getString("strName"));
            spCall.setString(3, json.getString("strDescription"));
            spCall.setString(4, json.getString("strSocials"));
            spCall.setString(5, json.getString("strWebPage"));
            spCall.setString(6, json.getString("strSegment"));
            spCall.setString(7, json.getString("strSpecialties"));
            spCall.setString(8, json.getString("strAddress"));
            spCall.setString(9, json.getString("strContact"));
            spCall.setInt(10, json.getInt("amtRating"));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }
    
    private static Object fnBusiness_UpdateSubDomain(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spBusiness_UpdateSubDomain(?, ?) }");
            spCall.setString(1, json.getString("id"));
            spCall.setString(2, json.getString("strSubDomain"));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }
    
    private static Object fnBusiness_Deactivate(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spBusiness_Deactivate(?) }");
        spCall.setString(1, request.getQueryParameters().get("id"));

        // Execute the procedure
        spCall.executeUpdate();

        return null;
    }
}
