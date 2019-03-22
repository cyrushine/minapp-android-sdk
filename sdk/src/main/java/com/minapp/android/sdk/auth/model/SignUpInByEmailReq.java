package com.minapp.android.sdk.auth.model;

import com.google.gson.annotations.SerializedName;

public class SignUpInByEmailReq {

    /**
     * email : aaa@bbb
     * password : isdkf asdf
     */

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public SignUpInByEmailReq(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignUpInByEmailReq() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
