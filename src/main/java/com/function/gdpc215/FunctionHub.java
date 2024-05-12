package com.function.gdpc215;

import com.function.gdpc215.logic.Availability;
import com.function.gdpc215.logic.BillDetails;
import com.function.gdpc215.logic.Bills;
import com.function.gdpc215.logic.Business;
import com.function.gdpc215.logic.Categories;
import com.function.gdpc215.logic.Config;
import com.function.gdpc215.logic.Coupon;
import com.function.gdpc215.logic.Discount;
import com.function.gdpc215.logic.Products;
import com.function.gdpc215.logic.User;
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

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

public class FunctionHub {
    @FunctionName("HttpExample")
    public HttpResponseMessage run(
        @HttpTrigger(
                name = "req", 
                methods = {HttpMethod.GET, HttpMethod.POST}, 
                route="{route}/{subroute}", 
                authLevel = AuthorizationLevel.ANONYMOUS
            ) 
            HttpRequestMessage<Optional<String>> request,
        @BindingName("route") String originalRoute,
        @BindingName("subroute") String originalSubroute,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java HTTP trigger processed a request.");
        String connectionString = System.getenv("KV-CONNECTION-STRING");

        String route = originalRoute.toLowerCase();
        String subroute = originalSubroute.toLowerCase();

        Object result = null;
        try {
            if(route.equals("availability")){
                result = Availability.hubAvailability(subroute, request, connectionString);
            }
            else if(route.equals("bill-details")){
                result = BillDetails.hubBillDetails(subroute, request, connectionString);
            }
            else if(route.equals("bill")){
                result = Bills.hubBills(subroute, request, connectionString);
            }
            else if(route.equals("business")){
                result = Business.hubBusiness(subroute, request, connectionString);
            }
            else if(route.equals("categories")){
                result = Categories.hubCategories(subroute, request, connectionString);
            }
            else if(route.equals("config")){
                result = Config.hubConfig(subroute, request, connectionString);
            }
            else if(route.equals("coupon")){
                result = Coupon.hubCoupon(subroute, request, connectionString);
            }
            else if(route.equals("discount")){
                result = Discount.hubDiscount(subroute, request, connectionString);
            }
            else if(route.equals("products")){
                result = Products.hubProducts(subroute, request, connectionString);
            }
            else if(route.equals("user")){
                result = User.hubUser(subroute, request, connectionString);
            }
            // else if(route.equals("availability-product")){
            //     return AvailabilityProductRelation.hubAvailabilityProductRelation(subroute, request, connectionString);
            // }
            // else if(route.equals("coupon-client")){
            //     return CouponClientRelation.hubCouponClientRelation(subroute, request, connectionString);
            // }
            // else if(route.equals("discount-product")){
            //     return DiscountProductRelation.hubDiscountProductRelation(subroute, request, connectionString);
            // }
            else {
                return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
            }
        } catch (JSONException e) {
            System.out.println("Path: " + request.getUri().getPath() + " || Error: " + e.getMessage() + "\r\n" + e.getStackTrace());

            LogUtils.ExceptionHandler(e);
            return request
                .createResponseBuilder(HttpStatus.BAD_REQUEST)
                .body("Request body is invalid. Exception: " + e.getMessage() + "\r\n" + e.getStackTrace())
                .build();
        } catch (Exception e) {
            System.out.println("Path: " + request.getUri().getPath() + " || Error: " + e.getMessage() + "\r\n" + e.getStackTrace());

            LogUtils.ExceptionHandler(e);
            return request
                .createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage() + "\r\n" + e.getStackTrace())
                .build();
        } 

        if (result instanceof HttpResponseMessage) {
            // Not found
            System.out.println("Path: " + request.getUri().getPath() + " || Result: URL not found");

            return (HttpResponseMessage) result;
        }
        
        if (result == null) {
            System.out.println("Path: " + request.getUri().getPath() + " || Result: Success. No body response.");

            return request
                .createResponseBuilder(HttpStatus.OK)
                .body(new Object())
                .build();
        }
        else {
            System.out.println("Path: " + request.getUri().getPath() + " || Result: " + new JSONObject(result).toString());

            return request
                .createResponseBuilder(HttpStatus.OK)
                .body(result)
                .build();
        }
        
    }    
}
