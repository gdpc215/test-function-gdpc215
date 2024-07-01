package com.function.gdpc215.logic;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.ConfigDB;
import com.function.gdpc215.model.ConfigEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Config {

    public static Object hubConfig(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnConfig_GetByKey(request, connectionString);
            case "get-by-bid" -> fnConfig_GetByBusiness(request, connectionString);
            case "insert" -> fnConfig_Insert(request, connectionString);
            case "update" -> fnConfig_Update(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnConfig_GetByKey(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String businessId = request.getQueryParameters().get("business-id");
        String key = request.getQueryParameters().get("key");

        ConfigEntity entity = ConfigDB.fnConfig_GetByKey(connectionString, businessId, key);
        return entity;
    }

    private static Object fnConfig_GetByBusiness(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String businessId = request.getQueryParameters().get("business-id");

        List<ConfigEntity> entities = ConfigDB.fnConfig_GetByBusiness(connectionString, businessId);
        return entities;
    }

    private static Object fnConfig_Insert(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            ConfigEntity entity = new ConfigEntity(jsonBody);

            ConfigDB.fnConfig_Insert(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnConfig_Update(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            ConfigEntity entity = new ConfigEntity(jsonBody);

            ConfigDB.fnConfig_Update(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

}
