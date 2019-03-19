package com.minapp.android.sdk.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.minapp.android.sdk.util.DateUtil;

import java.lang.reflect.Type;
import java.util.Calendar;

public class CalendarDeserializer implements JsonDeserializer<Calendar> {

    @Override
    public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.isJsonNull() ? null : DateUtil.parseDBDateString(json.getAsString());
    }

}
