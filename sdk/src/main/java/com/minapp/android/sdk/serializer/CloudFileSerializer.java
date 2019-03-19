package com.minapp.android.sdk.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.minapp.android.sdk.file.CloudFile;
import com.minapp.android.sdk.file.model.FileMetaResponse;

import java.lang.reflect.Type;

public class CloudFileSerializer implements JsonSerializer<CloudFile> {

    @Override
    public JsonElement serialize(CloudFile src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(new FileMetaResponse(src));
    }

}
