package com.minapp.android.sdk.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.gson.*;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.file.Category;
import com.minapp.android.sdk.file.CloudFile;
import com.minapp.android.sdk.file.model.FileMetaResponse;
import com.minapp.android.sdk.util.DateUtil;
import com.minapp.android.sdk.util.Util;

import java.lang.reflect.Type;
import java.util.Calendar;

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


    /*************************** array ***********************************/


    public RecordObject put(@NonNull String key, String[] value) {
        Util.assetNotNull(key);
        JsonArray array = null;
        if (value != null) {
            array = new JsonArray(value.length);
            for (String item : value) {
                array.add(item);
            }
        }
        jsonObject.add(key, array);
        return this;
    }

    public void put(@NonNull String key, Number[] value) {
        Util.assetNotNull(key);
        JsonArray array = null;
        if (value != null) {
            array = new JsonArray(value.length);
            for (Number item : value) {
                array.add(item);
            }
        }
        jsonObject.add(key, array);
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
        JsonElement json = null;
        if (value != null) {
            json = Global.gson().toJsonTree(new FileMetaResponse(value));
        }
        jsonObject.add(key, json);
        return this;
    }

    public @Nullable CloudFile getFile(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return new CloudFile(Global.gson().fromJson(jsonObject.getAsJsonObject(key), FileMetaResponse.class));
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
        if (obj == null) {
            jsonObject.add(key, null);
        } else {
            jsonObject.add(key, Global.gson().toJsonTree(obj));
        }
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
        jsonObject.addProperty(key, DateUtil.formatDBDateString(calendar));
        return this;
    }

    public @Nullable Calendar getCalendar(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return DateUtil.parseDBDateString(jsonObject.get(key).getAsString());
        } catch (Exception e) {
            Log.e(Const.TAG, e.getMessage(), e);
            return null;
        }
    }


    /*************************** number ***********************************/

}

