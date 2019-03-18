package com.minapp.android.sdk.file.model;

import com.google.gson.annotations.SerializedName;

public class UploadMetaBody {

    @SerializedName("filename")
    private String fileName;            // 上传的文件名

    @SerializedName("category_id")
    private String categoryId;          // 上传文件的所属分类，格式为文件分类的 ID 数组

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
