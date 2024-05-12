package com.function.gdpc215.utils;

import java.util.Optional;

public class SecurityUtils {
    public static Boolean isValidString (String str ){
        return true;
    }

    public static Boolean isValidRequestBody (Optional<String> body) {
        if (!body.isPresent()) return false;
        
        return true;
    }
}
