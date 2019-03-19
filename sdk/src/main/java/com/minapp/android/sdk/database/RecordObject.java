package com.minapp.android.sdk.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.gson.*;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.file.CloudFile;
import com.minapp.android.sdk.util.Function;
import com.minapp.android.sdk.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecordObject {

    private TableObject table;
    private JsonObject jsonObject;
    private RecordMeta meta;

    RecordObject(TableObject table) {
        this.table = table;
        jsonObject = new JsonObject();
    }

    JsonObject toJsonObject() {
        return jsonObject;
    }

    @NonNull Long tableId() {
        return table != null ? table.getTableId() : -1;
    }

    public @Nullable String id() {
        return meta != null ? meta.getId() : null;
    }

    /**
     * 以服务器上的数据为准，更新本地数据
     * @param json
     */
    void updateByServer(JsonObject json) {
        if (json == null) {
            this.meta = null;
        } else {

            RecordMeta meta = RecordMeta.fromJson(json);
            if (meta != null && meta.check()) {
                this.meta = meta;
                this.jsonObject = json;
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("tableId : ").append(tableId());
        if (jsonObject != null) {
            JsonObject clone = jsonObject.deepCopy();
            if (meta != null) {
                clone.addProperty(RecordMeta.ID, meta.getId());
                clone.addProperty(RecordMeta.CREATED_AT, meta.getCreatedAt());
                clone.addProperty(RecordMeta.CREATED_BY, meta.getCreatedBy());
                clone.addProperty(RecordMeta.UPDATED_AT, meta.getUpdatedAt());
                clone.add(RecordMeta.READ_PERM, Global.gson().toJsonTree(meta.getReadPerm()));
                clone.add(RecordMeta.WRITE_PERM, Global.gson().toJsonTree(meta.getWritePerm()));
            }
            sb.append("\n").append(Global.gson().toJson(clone));
        }
        return sb.toString();
    }

    /*************************** CURD ***********************************/


    public RecordObject save() throws Exception {
        Database.save(this);
        return this;
    }

    public void saveInBackground() {
        saveInBackground(null);
    }

    public void saveInBackground(final Callback callback) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    save();
                    if (callback != null) {
                        callback.onSuccess(RecordObject.this);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFailure(RecordObject.this, e);
                    }
                }
            }
        });
    }

    public void delete() throws Exception {
        Database.delete(this);
    }

    public void deleteInBackground() {
        deleteInBackground(null);
    }

    public void deleteInBackground(final Callback callback) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    delete();
                    if (callback != null) {
                        callback.onSuccess(RecordObject.this);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFailure(RecordObject.this, e);
                    }
                }
            }
        });
    }


    /*************************** string ***********************************/

    public RecordObject put(@NonNull String key, String value) {
        Util.assetNotNull(key);
        jsonObject.addProperty(key, value);
        return this;
    }

    public @Nullable String getString(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return jsonObject.get(key).getAsString();
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** number ***********************************/



    public RecordObject put(@NonNull String key, Number value) {
        Util.assetNotNull(key);
        jsonObject.addProperty(key, value);
        return this;
    }


    public @Nullable Number getNumber(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return jsonObject.get(key).getAsNumber();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable Integer getInt(@NonNull String key) {
        try {
            return getNumber(key).intValue();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable Long getLong(@NonNull String key) {
        try {
            return getNumber(key).longValue();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable Float getFloat(@NonNull String key) {
        try {
            return getNumber(key).floatValue();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable Double getDouble(@NonNull String key) {
        try {
            return getNumber(key).doubleValue();
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** boolean ***********************************/

    public RecordObject put(@NonNull String key, boolean value) {
        Util.assetNotNull(key);
        jsonObject.addProperty(key, value);
        return this;
    }

    public @Nullable Boolean getBoolean(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return jsonObject.get(key).getAsBoolean();
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** file ***********************************/

    public RecordObject put(@NonNull String key, CloudFile value) {
        Util.assetNotNull(key);
        jsonObject.add(key, Global.gson().toJsonTree(value));
        return this;
    }

    public @Nullable CloudFile getFile(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return Global.gson().fromJson(jsonObject.getAsJsonObject(key), CloudFile.class);
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** object ***********************************/

    /**
     * 这里通过 {@link Gson} 序列化 object <br />
     * 所以 object 要不用 gson 注解标注 property，要不在 proguard 里排除 object 的混淆
     * @param key
     * @param obj
     * @return
     */
    public RecordObject put(@NonNull String key, Object obj) {
        Util.assetNotNull(key);
        jsonObject.add(key, Global.gson().toJsonTree(obj));
        return this;
    }

    public <T> T get(@NonNull String key, Class<T> clz) {
        Util.assetNotNull(key);
        try {
            return Global.gson().fromJson(jsonObject.get(key), clz);
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** date（日期时间，ISO8601 格式的日期字符串，例如："2018-09-01T18:31:02.631000+08:00" ***********************************/

    public RecordObject put(@NonNull String key, Calendar calendar) {
        Util.assetNotNull(key);
        jsonObject.add(key, Global.gson().toJsonTree(calendar));
        return this;
    }

    public @Nullable Calendar getCalendar(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return Global.gson().fromJson(jsonObject.get(key), Calendar.class);
        } catch (Exception e) {
            Log.e(Const.TAG, e.getMessage(), e);
            return null;
        }
    }


    /*************************** array ***********************************/


    private  <T> RecordObject putArray(@NonNull String key, List<T> list) {
        Util.assetNotNull(key);
        JsonArray value = null;
        if (list != null) {
            value = new JsonArray(list.size());
            for (T item : list) {
                value.add(Global.gson().toJsonTree(item));
            }
        }
        jsonObject.add(key, value);
        return this;
    }

    private @Nullable <T> List<T> getArray(@NonNull String key, Function<JsonElement, T> transform) {
        Util.assetNotNull(key);
        try {
            JsonArray array = jsonObject.get(key).getAsJsonArray();
            List<T> list = new ArrayList<>(array.size());
            for (JsonElement elem : array) {
                list.add(transform.on(elem));
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }


    public RecordObject putStringArray(@NonNull String key, List<String> list) {
        return putArray(key, list);
    }

    public RecordObject putNumberArray(@NonNull String key, List<Number> list) {
        return putArray(key, list);
    }

    public RecordObject putBooleanArray(@NonNull String key, List<Boolean> list) {
        return putArray(key, list);
    }

    public RecordObject putFileArray(@NonNull String key, List<CloudFile> list) {
        return putArray(key, list);
    }

    public RecordObject putCalendarArray(@NonNull String key, List<Calendar> list) {
        return putArray(key, list);
    }

    public RecordObject putObjectArray(@NonNull String key, List<Object> list) {
        return putArray(key, list);
    }

    public @Nullable List<String> getStringArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, String>() {
            @Override
            public String on(JsonElement elem) {
                return elem.getAsString();
            }
        });
    }

    public @Nullable List<Integer> getIntArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Integer>() {
            @Override
            public Integer on(JsonElement elem) {
                return elem.getAsNumber().intValue();
            }
        });
    }

    public @Nullable List<Long> getLongArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Long>() {
            @Override
            public Long on(JsonElement elem) {
                return elem.getAsNumber().longValue();
            }
        });
    }

    public @Nullable List<Float> getFloatArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Float>() {
            @Override
            public Float on(JsonElement elem) {
                return elem.getAsNumber().floatValue();
            }
        });
    }

    public @Nullable List<Double> getDoubleArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Double>() {
            @Override
            public Double on(JsonElement elem) {
                return elem.getAsNumber().doubleValue();
            }
        });
    }

    public @Nullable List<Boolean> getBooleanArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Boolean>() {
            @Override
            public Boolean on(JsonElement elem) {
                return elem.getAsBoolean();
            }
        });
    }

    public @Nullable List<CloudFile> getFileArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, CloudFile>() {
            @Override
            public CloudFile on(JsonElement elem) {
                return Global.gson().fromJson(elem, CloudFile.class);
            }
        });
    }

    public @Nullable List<Calendar> getCalendarArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Calendar>() {
            @Override
            public Calendar on(JsonElement elem) {
                return Global.gson().fromJson(elem, Calendar.class);
            }
        });
    }

    public @Nullable <T> List<T> getObjectArray(@NonNull String key, final Class<T> clz) {
        return getArray(key, new Function<JsonElement, T>() {
            @Override
            public T on(JsonElement elem) {
                return Global.gson().fromJson(elem, clz);
            }
        });
    }
}

