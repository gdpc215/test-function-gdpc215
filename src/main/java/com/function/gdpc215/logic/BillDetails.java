package com.function.gdpc215.logic;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.BillDetailsDB;
import com.function.gdpc215.model.BillDetailEntity;
import com.function.gdpc215.model.BillDetailExtendedEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class BillDetails {

    public static Object hubBillDetails(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnBillDetails_GetById(request, connectionString);
            case "get-extended" -> fnBillDetails_GetExtendedById(request, connectionString);
            case "get-by-bill" -> fnBillDetails_GetByBill(request, connectionString);
            case "get-extended-by-bill" -> fnBillDetails_GetExtendedByBill(request, connectionString);
            case "insert" -> fnBillDetails_Insert(request, connectionString);
            case "update" -> fnBillDetails_Update(request, connectionString);
            case "delete" -> fnBillDetails_Delete(request, connectionString);
            case "clear" -> fnBillDetails_Clear(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnBillDetails_GetById(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String billDetailId = request.getQueryParameters().get("id");

        BillDetailEntity entity = BillDetailsDB.fnBillDetails_GetById(connectionString, billDetailId);
        return entity;
    }

    private static Object fnBillDetails_GetExtendedById(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        String billDetailId = request.getQueryParameters().get("id");

        BillDetailExtendedEntity entity = BillDetailsDB.fnBillDetails_GetExtendedById(connectionString, billDetailId);
        return entity;
    }

    private static Object fnBillDetails_GetByBill(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String billDetailId = request.getQueryParameters().get("bill-id");

        List<BillDetailEntity> entity = BillDetailsDB.fnBillDetails_GetByBill(connectionString, billDetailId);
        return entity;
    }

    private static Object fnBillDetails_GetExtendedByBill(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        String billDetailId = request.getQueryParameters().get("bill-id");

        List<BillDetailExtendedEntity> entity = BillDetailsDB.fnBillDetails_GetExtendedByBill(connectionString,
                billDetailId);
        return entity;
    }

    private static Object fnBillDetails_Insert(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            BillDetailEntity billDetailEntity = new BillDetailEntity(jsonBody);
            BillDetailsDB.fnBillDetails_Insert(connectionString, billDetailEntity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBillDetails_Update(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            BillDetailEntity billDetailEntity = new BillDetailEntity(jsonBody);
            BillDetailsDB.fnBillDetails_Update(connectionString, billDetailEntity);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBillDetails_Delete(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String billDetailId = request.getQueryParameters().get("id");

        BillDetailsDB.fnBillDetails_Delete(connectionString, billDetailId);
        return null;
    }

    private static Object fnBillDetails_Clear(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String billId = request.getQueryParameters().get("bill-id");

        BillDetailsDB.fnBillDetails_Clear(connectionString, billId);
        return null;
    }
}
