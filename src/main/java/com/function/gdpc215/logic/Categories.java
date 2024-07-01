package com.function.gdpc215.logic;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.CategoriesDB;
import com.function.gdpc215.model.CategoriesEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Categories {

    public static Object hubCategories(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnCategories_GetById(request, connectionString);
            case "get-by-bid" -> fnCategories_GetByBusiness(request, connectionString);
            case "insert" -> fnCategories_Insert(request, connectionString);
            case "update" -> fnCategories_Update(request, connectionString);
            case "change-order" -> fnCategories_ChangeOrderNumber(request, connectionString);
            case "delete" -> fnCategories_Delete(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnCategories_GetById(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String categoryId = request.getQueryParameters().get("id");

        CategoriesEntity entity = CategoriesDB.fnCategories_GetById(connectionString, categoryId);
        return entity;
    }

    private static Object fnCategories_GetByBusiness(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        String businessId = request.getQueryParameters().get("business-id");

        List<CategoriesEntity> entities = CategoriesDB.fnCategories_GetByBusiness(connectionString, businessId);
        return entities;
    }

    private static Object fnCategories_Update(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            CategoriesEntity entity = new CategoriesEntity(jsonBody);
            CategoriesDB.fnCategories_Update(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnCategories_Insert(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            CategoriesEntity entity = new CategoriesEntity(jsonBody);
            CategoriesDB.fnCategories_Insert(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnCategories_ChangeOrderNumber(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());

            String categoryId = jsonBody.optString("categoryId");
            Integer newOrderNumber = jsonBody.optInt("newOrderNumber");

            CategoriesDB.fnCategories_ChangeOrderNumber(connectionString, categoryId, newOrderNumber);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnCategories_Delete(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String categoryId = request.getQueryParameters().get("id");

        CategoriesDB.fnCategories_Delete(connectionString, categoryId);
        return null;
    }
}
