package com.function.gdpc215.utils.adapters;

import com.function.gdpc215.utils.Constants;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT);

    @Override
    public JsonElement serialize(LocalDateTime localDate, Type type,
            JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(localDate.format(formatter));
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), formatter);
    }

}