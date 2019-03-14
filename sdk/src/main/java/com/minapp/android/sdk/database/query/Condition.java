package com.minapp.android.sdk.database.query;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class Condition {

    private Operator operator;
    private String lvalue;
    private Object rvalue;

    Condition(Operator operator, String lvalue, Object rvalue) {
        this.operator = operator;
        this.lvalue = lvalue;
        this.rvalue = rvalue;
    }

    public static class Serializer implements JsonSerializer<Condition> {
        @Override
        public JsonElement serialize(Condition src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject root = new JsonObject();
            if (src.operator != null && src.lvalue != null) {
                JsonObject value = new JsonObject();
                value.add(src.operator.value, context.serialize(src.rvalue));
                root.add(src.lvalue, value);
            }
            return root;
        }
    }
}
