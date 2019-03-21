package com.minapp.android.sdk.serializer;

import com.google.gson.*;
import com.minapp.android.sdk.util.DateUtil;

import java.lang.reflect.Type;
import java.util.GregorianCalendar;

public class GregorianCalendarSerializer implements JsonSerializer<GregorianCalendar> {

    @Override
    public JsonElement serialize(GregorianCalendar src, Type typeOfSrc, JsonSerializationContext context) {
        String string = DateUtil.formatDBDateString(src);
        return string != null ? new JsonPrimitive(string) : JsonNull.INSTANCE;
    }

}
