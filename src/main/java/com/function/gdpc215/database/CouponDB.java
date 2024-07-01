package com.function.gdpc215.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.function.gdpc215.model.CouponEntity;
import com.function.gdpc215.utils.JsonUtilities;
import com.function.gdpc215.utils.Utils;

public class CouponDB {

    public static CouponEntity fnCoupon_Get(String connectionString, String id)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCoupon_GetByID(?) }");
        spCall.setString(1, id);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return CouponEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static CouponEntity fnCoupon_Get_ValidateCode(String connectionString, String businessId, String strCode)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCoupon_Get_ValidateCode(?, ?) }");
        spCall.setString(1, businessId);
        spCall.setString(2, strCode);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return CouponEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static CouponEntity fnCoupon_Get_ValidateCodeForClient(String connectionString, String businessId,
            String strCode, String userId) throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCoupon_Get_ValidateCodeForClient(?, ?, ?) }");
        spCall.setString(1, businessId);
        spCall.setString(2, strCode);
        spCall.setString(2, userId);
        // Execute operation
        ResultSet resultSet = spCall.executeQuery();
        // Cast result to appropiate type
        return CouponEntity.getSingleFromJsonArray(JsonUtilities.resultSetReader(resultSet));
    }

    public static void fnCoupon_Insert(String connectionString, CouponEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spCoupon_Insert(?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.businessId);
        spCall.setString(2, entity.strCode);
        spCall.setString(3, entity.strDescription);
        spCall.setString(4, entity.strDiscountType);
        spCall.setDouble(5, entity.amtCouponValue);
        spCall.setDate(6, Utils.dateConverterToSql(entity.dateExpiration));
        spCall.setInt(7, entity.amtRedemptionLimit);
        spCall.setBoolean(8, entity.flgSameClientReusage);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnCoupon_Update(String connectionString, CouponEntity entity)
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        CallableStatement spCall = connection.prepareCall("{ call spCoupon_Update(?, ?, ?, ?, ?, ?, ?, ?) }");
        spCall.setString(1, entity.id);
        spCall.setString(2, entity.strCode);
        spCall.setString(3, entity.strDescription);
        spCall.setString(4, entity.strDiscountType);
        spCall.setDouble(5, entity.amtCouponValue);
        spCall.setDate(6, Utils.dateConverterToSql(entity.dateExpiration));
        spCall.setInt(7, entity.amtRedemptionLimit);
        spCall.setBoolean(8, entity.flgSameClientReusage);
        // Execute operation
        spCall.executeUpdate();
    }

    public static void fnCoupon_Delete(String connectionString, String id) 
            throws Exception {
        Connection connection = DriverManager.getConnection(connectionString);
        // Prepare statement
        PreparedStatement spCall = connection.prepareCall("{ call spCoupon_Delete(?) }");
        spCall.setString(1, id);
        // Execute operation
        spCall.executeUpdate();
    }
}
