package com.function.gdpc215.utils;

import java.util.regex.Pattern;

public class Constants {
    public static String TIMEZONE = "America/Lima";
    public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static String TIME_FORMAT = "HH:mm:ss";
    public static Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    
}
