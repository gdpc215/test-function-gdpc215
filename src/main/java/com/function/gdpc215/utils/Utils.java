package com.function.gdpc215.utils;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class Utils {

    public static boolean validateUUID (String uuid) {
        try {
            // First validation: UUID accepts it
            UUID value = UUID.fromString(uuid);
            // Second validation: Matches pattern
            return Constants.UUID_REGEX.matcher(value.toString()).matches();
        }
        catch (Exception e) {
            return false;
        }
    }

    public static Date dateConverterToSql(LocalDateTime dateValue) {
        Instant instant = dateValue.toInstant(ZoneOffset.UTC);
        return new Date(instant.toEpochMilli());
    }
}
