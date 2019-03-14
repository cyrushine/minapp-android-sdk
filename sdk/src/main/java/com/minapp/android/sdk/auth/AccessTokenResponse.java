package com.minapp.android.sdk.auth;

import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {

    @SerializedName("access_token")
    private String accessToken;             // 用户授权的唯一票据

    @SerializedName("token_type")
    private String tokenType;               // token 类型

    @SerializedName("expires_in")
    private long expiresIn;                 // access_token 过期时间，单位是秒

    @SerializedName("refresh_token")
    private String refreshToken;            // 用于刷新授权有效期

    @SerializedName("scope")
    private String scope;                   // 权限范围

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
