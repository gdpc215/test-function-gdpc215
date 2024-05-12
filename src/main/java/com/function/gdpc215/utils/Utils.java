package com.function.gdpc215.utils;

import java.util.UUID;
import java.util.regex.Pattern;

public class Utils {
    //public static String DATE_FORMAT = "MMMM d, yyyy, hh:mm:ss aa";
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    //public static String TIME_FORMAT = "hh:mm:ss aa";
    public static String TIME_FORMAT = "HH:mm:ss.SSS";

    public static boolean validateUUID (String uuid) {
        try {
            // First validation: UUID accepts it
            UUID value = UUID.fromString(uuid);
            // Second validation: Matches pattern
            Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
            return UUID_REGEX.matcher(value.toString()).matches();
        }
        catch (Exception e) {
            return false;
        }
    }
}
