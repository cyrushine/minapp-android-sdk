package com.minapp.android.sdk.auth;

import com.google.gson.annotations.SerializedName;

public class CodeResponse {

    @SerializedName("code")
    private String code;            // 授权码

    @SerializedName("expires_in")
    private long expiresIn;         // code 的过期时间，单位是秒

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
