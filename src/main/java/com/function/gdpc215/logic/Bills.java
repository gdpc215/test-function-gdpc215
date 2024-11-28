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
            case "get-active-by-bid" -> fnBills_GetActiveByBusinessId(request, connectionString);
            case "get-by-bid" -> fnBills_GetByBusinessId(request, connectionString);
            case "update" -> fnBills_UpdateBill(request, connectionString);
            case "pay" -> fnBills_PayBill(request, connectionString);
            case "init" -> fnBills_InitBill(request, connectionString);
            case "clear" -> fnBills_ClearBill(request, connectionString);
            case "apply-coupon" -> fnBills_ApplyCoupon(request, connectionString);
            case "add-tip" -> fnBills_AddTip(request, connectionString);
            case "change-state" -> fnBills_ChangeState(request, connectionString);
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnBills_GetById(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String billId = request.getQueryParameters().get("id");
        return Bills.getBillById(connectionString, billId);
    }

    private static Object fnBills_GetByBusinessId(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String businessId = request.getQueryParameters().get("business-id");

        List<BillEntity> entity = BillsDB.fnBills_GetByBusinessId(connectionString, businessId);
        return entity;
    }

    private static Object fnBills_GetActiveByBusinessId(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String businessId = request.getQueryParameters().get("business-id");

        List<BillEntity> entities = BillsDB.fnBills_GetActiveByBusinessId(connectionString, businessId);

        entities.forEach((billEntity) -> {
            try {
                billEntity.billDetails.addAll(BillDetailsDB.fnBillDetails_GetExtendedByBill(connectionString, billEntity.billId));
            } catch (Exception ex) { }
        });

        return entities;
    }

    private static Object fnBills_PayBill(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());

            // Creating Bill entity
            BillEntity billEntity = new BillEntity(jsonBody);
            
            // Business logic
            billEntity.billState = 2; 

            // Storing Bill
            BillsDB.fnBills_UpdateBill(connectionString, billEntity); 

            // Getting updated bill
            return Bills.getBillById(connectionString, billEntity.billId);
        } else {
            throw new JSONException("Error al leer el cuerpo de la peticion");
        }
    }

    private static Object fnBills_UpdateBill(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());

            // Creating Bill entity
            BillEntity billEntity = new BillEntity(jsonBody);
            // Creating Bill details list
            List<BillDetailEntity> billDetailEntityList = BillDetailExtendedEntity
                    .getBaseObjCollectionFromJsonArray(jsonBody.getJSONArray("billDetails"), false);
            
            // Business logic
            billEntity.billState = 1; 

            // Storing Bill
            BillsDB.fnBills_UpdateBill(connectionString, billEntity);
            // Storing Bill details
            BillDetailsDB.fnBillDetails_InsertByBill(connectionString, billEntity.billId, billDetailEntityList);

            // Getting updated bill
            return Bills.getBillById(connectionString, billEntity.billId);
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

    private static Object fnBills_ClearBill(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        String billId = request.getQueryParameters().get("id");

        BillsDB.fnBills_ClearBill(connectionString, billId);

        return Bills.getBillById(connectionString, billId);
    }

    private static Object fnBills_ApplyCoupon(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {
        // Read request body
        Optional<String> body = request.getBody();
        if (SecurityUtils.isValidRequestBody(body)) {
            // Parse JSON request body
            JSONObject jsonBody = new JSONObject(body.get());

            String businessId = jsonBody.optString("businessId");
            String billId = jsonBody.optString("id");
            String couponCode = jsonBody.optString("couponCode");

            if (!businessId.equals("") && !billId.equals("") && !couponCode.equals("")) {
                BillsDB.fnBills_ApplyCoupon(connectionString, businessId, billId, couponCode);
                
                // Getting updated bill
                return Bills.getBillById(connectionString, billId);
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

    private static Object fnBills_ChangeState(HttpRequestMessage<Optional<String>> request, String connectionString)
            throws Exception {

        String billId = request.getQueryParameters().get("id");
        String newBillState = request.getQueryParameters().get("new-bill-state");

        BillsDB.fnBills_ChangeState(connectionString, billId, Integer.parseInt(newBillState));
        return null;
    }

    private static BillEntity getBillById (String connectionString, String billId) throws Exception {
        BillsDB.fnBills_CalculateTabAmount(connectionString, billId);
        BillEntity entity = BillsDB.fnBills_GetById(connectionString, billId);
        // Obtain the details for the bill
        if (!entity.billId.equals("")) {
            entity.billDetails = BillDetailsDB.fnBillDetails_GetExtendedByBill(connectionString, billId);
        }
        return entity;
    }
}
