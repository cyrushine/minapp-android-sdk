package com.minapp.android.sdk.auth.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class SignUpInResp {

    /**
     * user_id : 1235192341344
     * token : wefw
     * expires_in : 4325000
     * avatar : http://cdn.ifanr.cn/ifanr/default_avatar.png
     * nickname : riDGldYXLHouWIYx
     * _username : myusername
     * _email : myemail@somemail.com
     * _email_verified : false
     */

    @SerializedName("user_id")
    private Long userId;
    @SerializedName("token")
    private String token;
    @SerializedName("expires_in")
    private Integer expiresIn;      // 过期时间，单位：秒
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("_username")
    private String username;
    @SerializedName("_email")
    private String email;
    @SerializedName("_email_verified")
    private Boolean emailVerified;
    @SerializedName("_provider")
    private JsonElement provider;   // 用户在平台方的用户信息（见 v2.0/user/info 接口）

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public JsonElement getProvider() {
        return provider;
    }

    public void setProvider(JsonElement provider) {
        this.provider = provider;
    }
}
