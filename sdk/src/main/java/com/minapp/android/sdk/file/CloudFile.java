package com.minapp.android.sdk.file;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CloudFile {

    static final String SUCCESS = "success";

    private String cdnPath;
    private Long createdAt;
    private String id;
    private String mediaType;
    private String mimeType;
    private String name;
    private String path;
    private Long size;
    private String status;
    private List<Category> categories = new ArrayList<>(5);

    CloudFile(FileMetaResponse meta) {
        this.cdnPath = meta.getCdnPath();
        this.createdAt = meta.getCreatedAt();
        this.id = meta.getId();
        this.mediaType = meta.getMediaType();
        this.mimeType = meta.getMimeType();
        this.name = meta.getName();
        this.path = meta.getPath();
        this.size = meta.getSize();
        this.status = meta.getStatus();
        if (meta.getCategories() != null) {
            for (FileMetaResponse.Category item : meta.getCategories()) {
                this.categories.add(new Category(item));
            }
        }
    }

    public String getCdnPath() {
        return cdnPath;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Long getSize() {
        return size;
    }

    public boolean isUploadSuccess() {
        return SUCCESS.equalsIgnoreCase(this.status);
    }


    public static class Category {
        private String id;
        private String name;

        public Category(FileMetaResponse.Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
