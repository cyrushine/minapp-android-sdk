package com.minapp.android.sdk.file;

import com.minapp.android.sdk.file.category.CategoryInfo;

public class Category {

    private CategoryInfo data;

    Category(CategoryInfo data) {
        this.data = data;
    }

    public Long getCreatedAt() {
        return data.getCreatedAt();
    }

    public Long getFiles() {
        return data.getFiles();
    }

    public String getId() {
        return data.getId();
    }


    public String getName() {
        return data.getName();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("id=").append(getId()).append(",");
        sb.append("name=").append(getName()).append(",");
        sb.append("files=").append(getFiles()).append(",");
        sb.append("createAt=").append(getCreatedAt()).append(",");
        sb.append('}');
        return sb.toString();
    }
}
