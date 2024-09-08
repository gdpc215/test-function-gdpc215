package com.function.gdpc215;

import java.util.Optional;

import org.json.JSONException;

import com.function.gdpc215.logic.Availability;
import com.function.gdpc215.logic.BillDetails;
import com.function.gdpc215.logic.Bills;
import com.function.gdpc215.logic.Business;
import com.function.gdpc215.logic.Categories;
import com.function.gdpc215.logic.Config;
import com.function.gdpc215.logic.Coupon;
import com.function.gdpc215.logic.Discount;
import com.function.gdpc215.logic.Products;
import com.function.gdpc215.logic.Session;
import com.function.gdpc215.logic.Table;
import com.function.gdpc215.logic.User;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.LogUtils;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class FunctionHub {

    @FunctionName("SGAPI")
    public HttpResponseMessage SGAPI(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET,
        HttpMethod.POST}, route = "{route}/{subroute}", authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BindingName("route") String originalRoute,
            @BindingName("subroute") String originalSubroute,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");
        // String connectionString = System.getenv("KV-CONNECTION-STRING");
        //String connectionString = "jdbc:sqlserver://test-db-gdpc215.database.windows.net:1433;database=testdb;user=fastmenusg@test-db-gdpc215;password=gdpc215-5;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        String connectionString = "jdbc:sqlserver://localhost;database=testdb;user=fastmenusg;password=gdpc215-5;trustServerCertificate=true";
        String route = originalRoute.toLowerCase();
        String subroute = originalSubroute.toLowerCase();

        Object result;

        try {
            if (route.equals("session")) {
                result = Session.hubLogin(subroute, request, connectionString);
            } else {

                switch (route) {
                    case "availability" ->
                        result = Availability.hubAvailability(subroute, request, connectionString);
                    case "bill-details" ->
                        result = BillDetails.hubBillDetails(subroute, request, connectionString);
                    case "bill" ->
                        result = Bills.hubBills(subroute, request, connectionString);
                    case "business" ->
                        result = Business.hubBusiness(subroute, request, connectionString);
                    case "categories" ->
                        result = Categories.hubCategories(subroute, request, connectionString);
                    case "config" ->
                        result = Config.hubConfig(subroute, request, connectionString);
                    case "coupon" ->
                        result = Coupon.hubCoupon(subroute, request, connectionString);
                    case "discount" ->
                        result = Discount.hubDiscount(subroute, request, connectionString);
                    case "products" ->
                        result = Products.hubProducts(subroute, request, connectionString);
                    case "table" ->
                        result = Table.hubTable(subroute, request, connectionString);
                    case "user" ->
                        result = User.hubUser(subroute, request, connectionString);
                    default -> {
                        return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
                    }
                }
            }
        } catch (JSONException e) {
            System.out.println("Path: " + request.getUri().getPath() + " || Error: " + e.getMessage());
            LogUtils.ExceptionHandler(e);
            return request
                    .createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Request body is invalid. Exception: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            System.out.println("Path: " + request.getUri().getPath() + " || Error: " + e.getMessage());
            LogUtils.ExceptionHandler(e);
            return request
                    .createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage())
                    .build();
        }

        if (result instanceof HttpResponseMessage httpResponseMessage) {
            // Not found
            System.out.println("Path: " + request.getUri().getPath() + " || Result: URL not found");

            return httpResponseMessage;
        }

        if (result == null) {
            System.out.println("Path: " + request.getUri().getPath() + " || Result: Success. No body response.");

            return request
                    .createResponseBuilder(HttpStatus.OK)
                    .body(new Object())
                    .build();
        } else {
            System.out.println("Path: " + request.getUri().getPath() + " || Result: " + result);

            return request
                    .createResponseBuilder(HttpStatus.OK)
                    .body(JsonUtilities.getJsonStringFromObject(result))
                    .build();
        }
    }

    @FunctionName("HttpExample")
    public HttpResponseMessage HttpExample(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET,
        HttpMethod.POST}, route = "testing/{route}", authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BindingName("route") String originalRoute,
            final ExecutionContext context) {

        return request
                .createResponseBuilder(HttpStatus.OK)
                .body("Testing a 2nd: " + originalRoute)
                .build();
    }
}
