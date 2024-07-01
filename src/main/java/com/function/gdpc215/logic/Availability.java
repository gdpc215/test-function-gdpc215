package com.function.gdpc215.logic;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.AvailabilityDB;
import com.function.gdpc215.model.AvailabilityEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Availability {
    public static Object hubAvailability(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnAvailability_GetById(request, connectionString);
            case "get-by-bid" -> fnAvailability_GetByBusinessId(request, connectionString);
            case "insert" -> fnAvailability_Insert(request, connectionString);
            case "update" -> fnAvailability_Update(request, connectionString);
            case "delete" -> fnAvailability_Delete(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnAvailability_GetById(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String availabilityId = request.getQueryParameters().get("id");

        AvailabilityEntity entity = AvailabilityDB.fnAvailability_GetById(connectionString, availabilityId);
        return entity;
    }

    private static Object fnAvailability_GetByBusinessId(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        String businessId = request.getQueryParameters().get("business-id");

        List<AvailabilityEntity> entity = AvailabilityDB.fnAvailability_GetByBusinessId(connectionString, businessId);
        return entity;
    }

    private static Object fnAvailability_Insert(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            AvailabilityEntity availEntity = new AvailabilityEntity(jsonBody);
            AvailabilityDB.fnAvailability_Insert(connectionString, availEntity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnAvailability_Update(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            AvailabilityEntity availEntity = new AvailabilityEntity(jsonBody);
            AvailabilityDB.fnAvailability_Insert(connectionString, availEntity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnAvailability_Delete(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String availabilityId = request.getQueryParameters().get("id");

        AvailabilityDB.fnAvailability_Delete(connectionString, availabilityId);
        return null;
    }

}
