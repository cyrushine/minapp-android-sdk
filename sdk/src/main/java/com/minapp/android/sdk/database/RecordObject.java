package com.minapp.android.sdk.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.Util;

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

    @Nullable String id() {
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


    public void save() throws Exception {
        Database.save(this);
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

    public void put(@NonNull String key, String value) {
        Util.assetNotNull(key);
        jsonObject.addProperty(key, value);
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



    public void put(@NonNull String key, Number value) {
        Util.assetNotNull(key);
        jsonObject.addProperty(key, value);
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


    public void put(@NonNull String key, String[] value) {
        Util.assetNotNull(key);
        JsonArray array = null;
        if (value != null) {
            array = new JsonArray(value.length);
            for (String item : value) {
                array.add(item);
            }
        }
        jsonObject.add(key, array);
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

    public void put(@NonNull String key, boolean value) {
        Util.assetNotNull(key);
        jsonObject.addProperty(key, value);
    }

    public @Nullable Boolean getBoolean(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return jsonObject.get(key).getAsBoolean();
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** number ***********************************/


    /*************************** number ***********************************/


    /*************************** number ***********************************/


    /*************************** number ***********************************/

}

