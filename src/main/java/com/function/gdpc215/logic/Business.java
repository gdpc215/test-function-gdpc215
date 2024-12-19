package com.function.gdpc215.logic;

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.BusinessDB;
import com.function.gdpc215.model.BusinessEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.function.gdpc215.utils.Utils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Business {

    public static Object hubBusiness(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnBusiness_GetById(request, connectionString);
            case "get-by-subdomain" -> fnBusiness_GetBySubdomain(request, connectionString);
            case "insert" -> fnBusiness_Insert(request, connectionString);
            case "update" -> fnBusiness_Update(request, connectionString);
            case "update-subdomain" -> fnBusiness_UpdateSubDomain(request, connectionString);
            case "deactivate" -> fnBusiness_Deactivate(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnBusiness_GetById(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String businessId = request.getQueryParameters().get("id");

        if (businessId.equals("")) {
            return new Exception("ID can't be empty");
        } else if (!Utils.validateUUID(businessId)) {
            return new Exception("ID is not a valid UUID");
        } else {
            BusinessEntity entity = BusinessDB.fnBusiness_GetById(connectionString, businessId);
            return !entity.id.equals("") ? entity : new Exception("Invalid id");
        }
    }

    private static Object fnBusiness_GetBySubdomain(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        String subdomain = request.getQueryParameters().get("subdomain");

        if (subdomain.equals("")) {
            return new Exception("Subdomain can't be empty");
        } else {
            BusinessEntity entity = BusinessDB.fnBusiness_GetBySubdomain(connectionString, subdomain);
            return !entity.id.equals("") ? entity : new Exception("Invalid subdomain");
        }
    }

    private static Object fnBusiness_Insert(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            BusinessEntity entity = new BusinessEntity(jsonBody);

            BusinessDB.fnBusiness_Insert(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBusiness_Update(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            BusinessEntity entity = new BusinessEntity(jsonBody);

            BusinessDB.fnBusiness_Update(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBusiness_UpdateSubDomain(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            String businessId = jsonBody.optString("id");
            String strSubDomain = jsonBody.optString("strSubDomain");

            BusinessDB.fnBusiness_UpdateSubDomain(connectionString, businessId, strSubDomain);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBusiness_Deactivate(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String businessId = request.getQueryParameters().get("id");

        BusinessDB.fnBusiness_Deactivate(connectionString, businessId);
        return null;
    }
}
