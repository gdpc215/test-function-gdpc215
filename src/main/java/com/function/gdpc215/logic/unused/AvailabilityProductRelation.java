package com.function.gdpc215.logic.unused;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class AvailabilityProductRelation {
    public static Object hubAvailabilityProductRelation (String subRoute, HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        if (subRoute.equals("get-by-product")) {
            return fnAvailabilityProductRelation_GetByProduct(request, connectionString);
        } 
        else if (subRoute.equals("get-by-availability")) {
            return fnAvailabilityProductRelation_GetByAvailability(request, connectionString);
        }
        if (subRoute.equals("insert")) {
            return fnAvailabilityProductRelation_Insert(request, connectionString);
        } 
        else if (subRoute.equals("insert-array")) {
            return fnAvailabilityProductRelation_InsertArray(request, connectionString);
        }
        else if (subRoute.equals("delete")) {
            return fnAvailabilityProductRelation_Delete(request, connectionString);
        }
        else if (subRoute.equals("delete-by-product")) {
            return fnAvailabilityProductRelation_DeleteByProduct(request, connectionString);
        }
        else if (subRoute.equals("delete-by-availability")) {
            return fnAvailabilityProductRelation_DeleteByAvailability(request, connectionString);
        }
        else {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        }
    }
    
    private static Object fnAvailabilityProductRelation_GetByProduct(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spAvailabilityProductRelation_GetByProduct(?) }");
            spCall.setString(1, request.getQueryParameters().get("product-id"));
    
            // Call procedure
            ResultSet resultSet = spCall.executeQuery();
    
            // Read result (if applicable)
            return request.createResponseBuilder(HttpStatus.OK).body(JsonUtilities.resultSetReader(resultSet).toList()).build();
     
    }

    private static Object fnAvailabilityProductRelation_GetByAvailability(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spAvailabilityProductRelation_GetByAvailability(?) }");
            spCall.setString(1, request.getQueryParameters().get("availability-id"));
    
            // Call procedure
            ResultSet resultSet = spCall.executeQuery();
    
            // Read result (if applicable)
            return request.createResponseBuilder(HttpStatus.OK).body(JsonUtilities.resultSetReader(resultSet).toList()).build();
        
    }

    private static Object fnAvailabilityProductRelation_Insert(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
            // Read request body
            Optional<String> body = request.getBody();
            if (SecurityUtils.isValidRequestBody(body)) {
                // Parse JSON request body
                JSONObject json = new JSONObject(body.get());
        
                // Prepare statement and execute insert operation
                try (PreparedStatement spCall = connection.prepareCall("{ call spAvailabilityProductRelation_Insert(?, ?) }")) {
                    spCall.setString(1, json.optString("availabilityId"));
                    spCall.setString(2, json.optString("productId"));
                    spCall.executeUpdate();
                }
        
                // Return success message
                return request.createResponseBuilder(HttpStatus.OK).build();
            }
            else {
                throw new JSONException("Error al leer el cuerpo de la peticion");
            }
        
    }
 
    private static Object fnAvailabilityProductRelation_InsertArray(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
            // Read request body
            Optional<String> body = request.getBody();
            if (SecurityUtils.isValidRequestBody(body)) {
                // Parse JSON request body
                JSONObject json = new JSONObject(body.get());
                
                // Prepare statement and execute insert operation
                try (PreparedStatement spCall = connection.prepareCall("{ call spAvailabilityProductRelation_InsertArray(?, ?) }")) {
                    spCall.setString(1, json.optString("availabilityIdArray"));
                    spCall.setString(2, json.optString("productId"));
                    spCall.executeUpdate();
                }
        
                // Return success message
                return request.createResponseBuilder(HttpStatus.OK).build();
            }
            else {
                throw new JSONException("Error al leer el cuerpo de la peticion");
            }
        
    }

    private static Object fnAvailabilityProductRelation_Delete(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spAvailabilityProductRelation_Delete(?, ?) }");
            spCall.setString(1, request.getQueryParameters().get("availability-id"));
            spCall.setString(2, request.getQueryParameters().get("product-id"));

            // Execute delete operation
            spCall.executeUpdate();

            // Return success message
            return request.createResponseBuilder(HttpStatus.OK).build();
        
    }

    private static Object fnAvailabilityProductRelation_DeleteByProduct(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spAvailabilityProductRelation_DeleteByProduct(?) }");
            spCall.setString(1, request.getQueryParameters().get("product-id"));

            // Execute delete operation
            spCall.executeUpdate();

            // Return success message
            return request.createResponseBuilder(HttpStatus.OK).build();
       
    }

    private static Object fnAvailabilityProductRelation_DeleteByAvailability(HttpRequestMessage<Optional<String>> request, String connectionString) throws Exception { 
        Connection connection = DriverManager.getConnection(connectionString);
            // Prepare statement
            PreparedStatement spCall = connection.prepareCall("{ call spAvailabilityProductRelation_DeleteByAvailability(?) }");
            spCall.setString(1, request.getQueryParameters().get("availability-id"));

            // Execute delete operation
            spCall.executeUpdate();

            // Return success message
            return request.createResponseBuilder(HttpStatus.OK).build();
        
    }

}

