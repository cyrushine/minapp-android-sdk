package com.minapp.android.sdk.auth;

import com.google.gson.annotations.SerializedName;

public class CodeRequest {

    @SerializedName("client_id")
    private String clientId;        // 知晓云应用的 ClientID

    @SerializedName("client_secret")
    private String clientSecret;    // 知晓云应用的 ClientSecret

    public CodeRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
