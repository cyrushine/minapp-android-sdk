package com.minapp.android.sdk.typeadapter;

import com.google.gson.*;
import com.minapp.android.sdk.util.DateUtil;

import java.lang.reflect.Type;
import java.util.Calendar;

public class CalendarSerializer implements JsonSerializer<Calendar> {

    @Override
    public JsonElement serialize(Calendar src, Type typeOfSrc, JsonSerializationContext context) {
        String string = DateUtil.formatDBDateString(src);
        return string != null ? new JsonPrimitive(string) : JsonNull.INSTANCE;
    }

}
