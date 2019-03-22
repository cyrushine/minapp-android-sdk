package com.minapp.android.sdk.auth.model;

import com.google.gson.annotations.SerializedName;

public class SignUpInByUsernameReq {

    /**
     * username : wefw
     * password : isdkf asdf
     */

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public SignUpInByUsernameReq(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SignUpInByUsernameReq() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
