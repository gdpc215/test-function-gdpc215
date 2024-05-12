package com.function.gdpc215.logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Config {
    
    public static Object hubConfig(String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        if (subRoute.equals("insert")) {
            return fnConfig_Insert(request, connectionString);
        }
        else if (subRoute.equals("update")) {
            return fnConfig_Update(request, connectionString);
        }
        else {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        }
    }

    private static Object fnConfig_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spConfig_Insert(?, ?, ?, ?) }");
            spCall.setString(1, json.getString("id"));
            spCall.setString(2, json.getString("businessId"));
            spCall.setString(3, json.getString("strValue"));
            spCall.setString(4, json.getString("strDescription"));
    
            // Execute insert operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnConfig_Update(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject json = new JSONObject(body.get());
            
            // Extract parameters from JSON and Prepare statement in a single line
            CallableStatement spCall = connection.prepareCall("{ call spConfig_Update(?, ?, ?) }");
            spCall.setString(1, json.getString("id"));
            spCall.setString(2, json.getString("strValue"));
            spCall.setString(3, json.getString("strDescription"));
    
            // Execute update operation
            spCall.executeUpdate();

            return null;
        }
        else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

}
