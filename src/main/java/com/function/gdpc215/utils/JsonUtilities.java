package com.function.gdpc215.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.function.gdpc215.utils.adapters.LocalDateAdapter;
import com.function.gdpc215.utils.adapters.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static LocalDateTime getParsedLocalDateTime(String value) {
        try {
            return switch (value.length()) {
                case 19 -> LocalDateTime.parse(value + ".000", DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT));
                case 20 -> LocalDateTime.parse(value + "000", DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT));
                case 21 -> LocalDateTime.parse(value + "00", DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT));
                case 22 -> LocalDateTime.parse(value + "0", DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT));
                default -> null;
            };
        } catch (Exception ex) {
            LogUtils.ExceptionHandler(ex);
            return null;
        }
    }

    public static Time getParsedTime(String value) {
        // public static String TIME_FORMAT = "hh:mm:ss aa";

        if (value == null || value.isEmpty() || value.equals("")) {
            return null;
        }
        try {
            return new Time((new SimpleDateFormat(Constants.TIME_FORMAT)).parse(value).getTime());
        } catch (ParseException ex) {
            LogUtils.ExceptionHandler(ex);
            return null;
        }
    }

    public static String getJsonStringFromObject(Object object) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        return gson.toJson(object);
    }
}
