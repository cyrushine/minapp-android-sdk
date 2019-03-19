package com.minapp.android.sdk.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.minapp.android.sdk.util.DateUtil;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GregorianCalendarDeserializer implements JsonDeserializer<GregorianCalendar> {

    @Override
    public GregorianCalendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            Calendar calendar = DateUtil.parseDBDateString(json.getAsString());
            GregorianCalendar retVal = new GregorianCalendar();
            retVal.setTimeZone(calendar.getTimeZone());
            retVal.setTimeInMillis(calendar.getTimeInMillis());
            return retVal;
        } catch (Exception e) {
            return null;
        }
    }
}
