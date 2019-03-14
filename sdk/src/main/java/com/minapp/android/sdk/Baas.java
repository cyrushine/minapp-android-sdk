package com.minapp.android.sdk;

import com.minapp.android.sdk.auth.Auth;

public class Baas {
    private Baas() {}

    /**
     * 完成 sdk 的初始化
     * @param clientId      ID 为知晓云应用的 ClientID，可通过知晓云管理后台进行获取
     * @param clientSecret  Secert 为知晓云应用的 ClientSecret，可通过知晓云管理后台进行获取
     */
    public static void init(String clientId, String clientSecret) {
        Auth.init(clientId, clientSecret);
    }

}
