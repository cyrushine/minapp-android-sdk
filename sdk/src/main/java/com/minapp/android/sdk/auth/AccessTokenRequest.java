package com.minapp.android.sdk.auth;

import com.google.gson.annotations.SerializedName;

public class AccessTokenRequest {

    public static final String GRANT_TYPE = "authorization_code";

    @SerializedName("client_id")
    private String clientId;                    // 知晓云应用的 ClientID

    @SerializedName("client_secret")
    private String clientSecret;                // 知晓云应用的 ClientSecret

    @SerializedName("code")
    private String code;                        // 授权码，通过上一步获取到的

    @SerializedName("grant_type")
    private String grantType = GRANT_TYPE;     // 授权类型，这里需指定为 GRANT_TYPE


    public AccessTokenRequest() {
    }

    public AccessTokenRequest(String clientId, String clientSecret, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
