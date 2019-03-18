package com.minapp.android.sdk.file.category;

import com.google.gson.annotations.SerializedName;

public class CreateCategoryBody {

    @SerializedName("name")
    private String name;

    public CreateCategoryBody(String name) {
        this.name = name;
    }

    public CreateCategoryBody() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
