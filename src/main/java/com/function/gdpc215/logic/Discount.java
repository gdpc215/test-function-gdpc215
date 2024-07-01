package com.function.gdpc215.logic;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.DiscountDB;
import com.function.gdpc215.model.DiscountEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Discount {

    public static Object hubDiscount(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnDiscount_Get(request, connectionString);
            case "get-by-bid" -> fnDiscount_GetByBusinessId(request, connectionString);
            case "insert" -> fnDiscount_Insert(request, connectionString);
            case "update" -> fnDiscount_Update(request, connectionString);
            case "delete" -> fnDiscount_Delete(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnDiscount_Get(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String discountId = request.getQueryParameters().get("id");

        DiscountEntity entity = DiscountDB.fnDiscount_Get(connectionString, discountId);
        return entity;
    }

    private static Object fnDiscount_GetByBusinessId(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        String businessId = request.getQueryParameters().get("business-id");

        List<DiscountEntity> entities = DiscountDB.fnDiscount_GetByBusinessId(connectionString, businessId);
        return entities;
    }

    private static Object fnDiscount_Insert(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            DiscountEntity entity = new DiscountEntity(jsonBody);
            DiscountDB.fnDiscount_Insert(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnDiscount_Update(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            DiscountEntity entity = new DiscountEntity(jsonBody);
            DiscountDB.fnDiscount_Update(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnDiscount_Delete(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String discountId = request.getQueryParameters().get("id");

        DiscountDB.fnDiscount_Delete(connectionString, discountId);
        return null;
    }
}
