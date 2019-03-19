package com.minapp.android.sdk.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.minapp.android.sdk.file.CloudFile;
import com.minapp.android.sdk.file.model.FileMetaResponse;

import java.lang.reflect.Type;

public class CloudFileDeserializer implements JsonDeserializer<CloudFile> {

    @Override
    public CloudFile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        FileMetaResponse data = context.deserialize(json, FileMetaResponse.class);
        return new CloudFile(data);
    }
}
