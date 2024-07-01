package com.function.gdpc215.logic;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.function.gdpc215.database.BillDetailsDB;
import com.function.gdpc215.database.BillsDB;
import com.function.gdpc215.model.BillDetailEntity;
import com.function.gdpc215.model.BillDetailExtendedEntity;
import com.function.gdpc215.model.BillEntity;
import com.function.gdpc215.utils.SecurityUtils;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class Bills {
    public static Object hubBills(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnBills_GetById(request, connectionString);
            case "get-by-bid" -> fnBills_GetByBusinessId(request, connectionString);
            case "update" -> fnBills_UpdateBill(request, connectionString);
            case "init" -> fnBills_InitBill(request, connectionString);
            case "calculate-tab" -> fnBills_CalculateTabAmount(request, connectionString);
            case "apply-coupon" -> fnBills_ApplyCoupon(request, connectionString);
            case "add-tip" -> fnBills_AddTip(request, connectionString);
            case "close" -> fnBills_CloseBill(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnBills_GetById(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String billDetailId = request.getQueryParameters().get("id");
        BillEntity entity = BillsDB.fnBills_GetById(connectionString, billDetailId);
        // Obtain the details for the bill
        if (entity.billId != "") {
            entity.billDetails = BillDetailsDB.fnBillDetails_GetExtendedByBill(connectionString, billDetailId);
        }
        return entity;
    }

    private static Object fnBills_GetByBusinessId(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String businessId = request.getQueryParameters().get("business-id");

        List<BillEntity> entity = BillsDB.fnBills_GetByBusinessId(connectionString, businessId);
        return entity;
    }

    private static Object fnBills_UpdateBill(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());
            BillEntity billEntity = new BillEntity(jsonBody);
            BillsDB.fnBills_UpdateBill(connectionString, billEntity);
            /* Inserting bill details */
            List<BillDetailEntity> billDetailEntityList = BillDetailExtendedEntity
                    .getBaseObjCollectionFromJsonArray(jsonBody.getJSONArray("billDetails"));
            BillDetailsDB.fnBillDetails_InsertByBill(connectionString, billEntity.billId, billDetailEntityList);
            return null;
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_InitBill(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());

            String businessId = jsonBody.optString("businessId");
            String tableId = jsonBody.optString("tableId");
            String userId = jsonBody.optString("userId");

            if (!businessId.equals("") && !tableId.equals("") && !userId.equals("")) {
                BillEntity entity = BillsDB.fnBills_InitBill(connectionString, businessId, tableId, userId);
                return entity;
            } else {
                throw new Exception("Los parametros no pueden estar vacios");
            }
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_CalculateTabAmount(HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        String billId = request.getQueryParameters().get("id");

        BillsDB.fnBills_CalculateTabAmount(connectionString, billId);
        return null;
    }

    private static Object fnBills_ApplyCoupon(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());

            String businessId = jsonBody.optString("businessId");
            String id = jsonBody.optString("id");
            String couponCode = jsonBody.optString("couponCode");

            if (!businessId.equals("") && !id.equals("") && !couponCode.equals("")) {
                BillsDB.fnBills_ApplyCoupon(connectionString, businessId, id, couponCode);
                return null;
            } else {
                throw new Exception("Los parametros no pueden estar vacios");
            }
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_AddTip(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());

            String id = jsonBody.optString("id");
            double amtTip = jsonBody.optDouble("amtTip");

            if (!id.equals("")) {
                BillsDB.fnBills_AddTip(connectionString, id, amtTip);
                return null;
            } else {
                throw new Exception("Los parametros no pueden estar vacios");
            }
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_CloseBill(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {

        String billId = request.getQueryParameters().get("id");

        BillsDB.fnBills_CloseBill(connectionString, billId);
        return null;
    }

}
