package com.minapp.android.sdk.file.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FileMetaResponse {

    /**
     * categories : [{"id":"5a1ba7b708443e7fc5f2fb18","name":"Category"}]
     * cdn_path : 1eJCS1MFGdvaaBoV.png
     * created_at : 1511762369
     * id : 5a1ba9c1fff1d651135e5ff1
     * media_type : image
     * mime_type : image/png
     * name : box_close.png
     * path : https://cloud-minapp-287.cloud.ifanrusercontent.com/1eJCS1MFGdvaaBoV.png
     * size : 3652
     * status : success
     */

    @SerializedName("cdn_path")
    private String cdnPath;
    @SerializedName("created_at")
    private long createdAt;
    @SerializedName("id")
    private String id;
    @SerializedName("media_type")
    private String mediaType;
    @SerializedName("mime_type")
    private String mimeType;
    @SerializedName("name")
    private String name;
    @SerializedName("path")
    private String path;
    @SerializedName("size")
    private long size;
    @SerializedName("status")
    private String status;
    @SerializedName("categories")
    private List<Category> categories;

    public String getCdnPath() {
        return cdnPath;
    }

    public void setCdnPath(String cdnPath) {
        this.cdnPath = cdnPath;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public static class Category {
        /**
         * id : 5a1ba7b708443e7fc5f2fb18
         * name : Category
         */

        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

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
}
