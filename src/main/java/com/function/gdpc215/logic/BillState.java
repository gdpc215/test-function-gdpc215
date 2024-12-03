package com.function.gdpc215.logic;

import java.util.List;
import java.util.Optional;

import com.function.gdpc215.database.BillStateDB;
import com.function.gdpc215.model.BillStateEntity;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpStatus;

public class BillState {
    public static Object hubBillState(String subRoute, HttpRequestMessage<Optional<String>> request,
            String connectionString) throws Exception {
        return switch (subRoute) {
            case "get" -> fnBillState_Get(connectionString);
            case "get-waiter-tabs" -> fnBillState_GetWaiterTabs(connectionString);
            
            default -> request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        };
    }

    private static Object fnBillState_Get(String connectionString)
            throws Exception {
        return BillStateDB.fnBillStatus_Get(connectionString);
    }

    private static Object fnBillState_GetWaiterTabs(String connectionString)
            throws Exception {
        List<BillStateEntity> list = BillStateDB.fnBillStatus_Get(connectionString);
        list.removeIf(x -> x.billGeneralState != 1);
        return list;
    }
}
