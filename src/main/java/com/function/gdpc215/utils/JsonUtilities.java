package com.function.gdpc215.utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtilities {
    public static JSONArray resultSetReader(ResultSet resultSet) {
        JSONArray jsonArray = new JSONArray();
        try {
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                JSONObject result = new JSONObject();
                for (int i = 0; i < columns; i++)
                    result.put(resultSet.getMetaData().getColumnLabel(i + 1), resultSet.getObject(i + 1));
                    jsonArray.put(result);
            }
        } catch (SQLException e) {
            LogUtils.ExceptionHandler(e);
        }
        return jsonArray;
    }

    public static Object[] jsonArrayToArray(JSONArray jsonArray) {
        List<Object> list  = jsonArray.toList();
        Object[] billArray = new Object[list.size()];
        return list.toArray(billArray);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonArrayToList(JSONArray jsonArray, Class<T> classType) {
        List<T> resultList = new ArrayList<>();
        
        for (int i = 0; i < jsonArray.length(); i++) {
            T object = (T) jsonArray.optJSONObject(i); // Assuming you have a method to convert JSONObject to T
            resultList.add(object);
        }
        
        return resultList;
    }
}
