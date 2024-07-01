package com.function.gdpc215.logic;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.TableDB;
import com.function.gdpc215.model.TableEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.function.gdpc215.utils.Utils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Table {

    public static Object hubTable(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnTable_GetById(request, connectionString);
            case "get-by-bid" -> fnTable_GetByBusiness(request, connectionString);
            case "validate-table" -> fnTable_ValidateTable(request, connectionString);
            case "insert" -> fnTable_Insert(request, connectionString);
            case "update" -> fnTable_Update(request, connectionString);
            case "update-active-users" -> fnTable_UpdateActiveUsers(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnTable_GetById(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String tableId = request.getQueryParameters().get("id");
        if (tableId.equals("")) {
            return new Exception("ID can't be empty");
        } else if (!Utils.validateUUID(tableId)) {
            return new Exception("ID is not a valid UUID");
        } else {
            TableEntity entity = TableDB.fnTable_GetById(connectionString, tableId);
            return entity != null ? entity : new Exception("Invalid table id");
        }
    }

    private static Object fnTable_GetByBusiness(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String businessId = request.getQueryParameters().get("business-id");

        List<TableEntity> entities = TableDB.fnTable_GetByBusiness(connectionString, businessId);
        return entities;
    }

    private static Object fnTable_ValidateTable(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String businessId = request.getQueryParameters().get("business-id");
        String tableNumber = request.getQueryParameters().get("table-number");

        if (businessId.equals("") || tableNumber.equals("")) {
            return new Exception("Parameters can't be empty");
        } else {
            TableEntity entity = TableDB.fnTable_ValidateTable(connectionString, businessId, tableNumber);
            return !entity.id.equals("") ? entity : new Exception("Invalid table number");
        }
    }

    private static Object fnTable_Insert(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            TableEntity entity = new TableEntity(jsonBody);
            TableDB.fnTable_Insert(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnTable_Update(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            TableEntity entity = new TableEntity(jsonBody);
            TableDB.fnTable_Update(connectionString, entity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnTable_UpdateActiveUsers(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            String id = jsonBody.optString("id");
            Boolean flgAction = jsonBody.optBoolean("strSubDomain");

            TableDB.fnTable_UpdateActiveUsers(connectionString, id, flgAction);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }
}
