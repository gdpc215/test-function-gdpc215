package com.function.gdpc215.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                for (int i = 0; i < columns; i++) {
                    result.put(resultSet.getMetaData().getColumnLabel(i + 1), resultSet.getObject(i + 1));
                }
                jsonArray.put(result);
            }
        } catch (SQLException e) {
            LogUtils.ExceptionHandler(e);
        }
        return jsonArray;
    }

    public static Object[] jsonArrayToArray(JSONArray jsonArray) {
        List<Object> list = jsonArray.toList();
        Object[] billArray = new Object[list.size()];
        return list.toArray(billArray);
    }

		public static Date getDateFromJsonString(String value) {
			return getDateFromJsonString(value, false);
		}

    public static Date getDateFromJsonString(String value, Boolean useSecondFormat) {
        //public static String DATE_FORMAT = "MMMM d, yyyy, hh:mm:ss aa";
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
				if (useSecondFormat) {
					DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
				}

        if (value == null || value.isEmpty() || value.equals("")) {
            return null;
        }
        try {
            return new Date((new SimpleDateFormat(DATE_FORMAT)).parse(value).getTime());
        } catch (ParseException ex) {
            LogUtils.ExceptionHandler(ex);
            return null;
        }
    }

    public static Time getTimeFromJsonString(String value) {
        //public static String TIME_FORMAT = "hh:mm:ss aa";
        String TIME_FORMAT = "HH:mm:ss";

        if (value == null || value.isEmpty() || value.equals("")) {
            return null;
        }
        try {
            return new Time((new SimpleDateFormat(TIME_FORMAT)).parse(value).getTime());
        } catch (ParseException ex) {
            LogUtils.ExceptionHandler(ex);
            return null;
        }
    }
}
