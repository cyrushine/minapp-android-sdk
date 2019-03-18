package com.minapp.android.sdk.file.category;

import com.google.gson.annotations.SerializedName;

public class CategoryInfo {

    /**
     * created_at : 1552890819
     * files : 0
     * id : 5c8f3bc3d32ca920a350fd1b
     * name : test
     * parent : null
     * subcategories : []
     */

    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("files")
    private Long files;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getFiles() {
        return files;
    }

    public void setFiles(Long files) {
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
