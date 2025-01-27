package com.opDTO.beans;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class SQLDateAdaptor implements JsonDeserializer<Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            String dateStr = json.getAsString();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                java.util.Date utilDate = dateFormat.parse(dateStr);
                return new Date(utilDate.getTime());
            } catch (Exception e) {
                throw new JsonParseException("Invalid date format: " + dateStr, e);
            }
        }
        throw new JsonParseException("Invalid date format");
    }

}
