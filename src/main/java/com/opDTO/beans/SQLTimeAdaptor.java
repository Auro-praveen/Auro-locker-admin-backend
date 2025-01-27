package com.opDTO.beans;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import com.auro.beans.CustomerEvents;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


public class SQLTimeAdaptor implements JsonDeserializer<Time> {

    private static final String TIME_FORMAT = "HH:mm:ss";

    @Override
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            String timeStr = json.getAsString();
            try {
                SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
                java.util.Date utilTime = timeFormat.parse(timeStr);
                return new Time(utilTime.getTime());
            } catch (Exception e) {
                throw new JsonParseException("Invalid time format: " + timeStr, e);
            }
        }
        throw new JsonParseException("Invalid time format");
    }
}
