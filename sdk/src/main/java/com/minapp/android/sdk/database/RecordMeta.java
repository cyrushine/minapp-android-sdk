package com.minapp.android.sdk.database;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.minapp.android.sdk.Global;

import java.util.List;

class RecordMeta {

    static final String ID = "id";
    static final String CREATED_AT = "created_at";
    static final String CREATED_BY = "created_by";
    static final String UPDATED_AT = "updated_at";
    static final String WRITE_PERM = "write_perm";
    static final String READ_PERM = "read_perm";

    @SerializedName(ID)
    private String id;

    @SerializedName(CREATED_AT)
    private Long createdAt;

    @SerializedName(CREATED_BY)
    private Long createdBy;

    @SerializedName(UPDATED_AT)
    private Long updatedAt;

    @SerializedName(WRITE_PERM)
    private List<String> writePerm;

    @SerializedName(READ_PERM)
    private List<String> readPerm;


    static @Nullable RecordMeta fromJson(JsonElement json) {
        RecordMeta obj = null;
        if (json != null) {
            TypeAdapter<RecordMeta> adapter = Global.gson().getAdapter(RecordMeta.class);
            if (adapter != null) {
                obj = adapter.fromJsonTree(json);
            }
        }
        return obj;
    }

    boolean check() {
        return !TextUtils.isEmpty(id);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getWritePerm() {
        return writePerm;
    }

    public void setWritePerm(List<String> writePerm) {
        this.writePerm = writePerm;
    }

    public List<String> getReadPerm() {
        return readPerm;
    }

    public void setReadPerm(List<String> readPerm) {
        this.readPerm = readPerm;
    }
}
