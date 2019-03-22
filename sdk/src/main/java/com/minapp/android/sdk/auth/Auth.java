package com.minapp.android.sdk.auth;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.HttpApi;
import com.minapp.android.sdk.auth.model.SignUpInByEmailReq;
import com.minapp.android.sdk.auth.model.SignUpInByUsernameReq;
import com.minapp.android.sdk.auth.model.SignUpInResp;
import com.minapp.android.sdk.exception.AuthException;
import com.minapp.android.sdk.exception.HttpException;
import com.minapp.android.sdk.util.ContentTypeInterceptor;
import com.minapp.android.sdk.util.MemoryCookieJar;
import okhttp3.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public abstract class Auth {

    private static String CLIENT_ID;
    private static final MemoryCookieJar COOKIE_JAR = new MemoryCookieJar();
    private static HttpApi API;
    private static final Object API_LOCK = new Object();
    private static SignUpInResp AUTH_INFO;


    public static void init(String clientId) {
        CLIENT_ID = clientId;
    }

    /**
     * 邮箱注册
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static SignUpInResp signUpByEmail(String email, String pwd) throws Exception {
        return Global.httpApi().signUpByEmail(new SignUpInByEmailReq(email, pwd)).execute().body();
    }

    /**
     * 用户名注册
     * @param username
     * @param pwd
     * @return
     * @throws Exception
     */
    public static SignUpInResp signUpByUsername(String username, String pwd) throws Exception {
        return Global.httpApi().signUpByUsername(new SignUpInByUsernameReq(username, pwd)).execute().body();
    }

    /**
     * 邮箱登录
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static SignUpInResp signInByEmail(String email, String pwd) throws Exception {
        SignUpInResp info = Global.httpApi().signInByEmail(new SignUpInByEmailReq(email, pwd)).execute().body();
        signIn(info);
        return info;
    }

    /**
     * 用户名登录
     * @param username
     * @param pwd
     * @return
     * @throws Exception
     */
    public static SignUpInResp signInByUsername(String username, String pwd) throws Exception {
        SignUpInResp info = Global.httpApi().signInByUsername(new SignUpInByUsernameReq(username, pwd)).execute().body();
        signIn(info);
        return info;
    }

    /**
     * 登录成功后，保存用户信息
     * @param info
     */
    private static void signIn(SignUpInResp info) {
        if (info != null) {
            AUTH_INFO = Global.gson().fromJson(Global.gson().toJson(info), SignUpInResp.class);
        }
    }

    static @Nullable String clientId() {
        return CLIENT_ID;
    }

    static @Nullable String token() {
        return AUTH_INFO != null ? AUTH_INFO.getToken() : null;
    }

    private static HttpApi httpApi() {
        if (API == null) {
            synchronized (API_LOCK) {
                if (API == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .followRedirects(true)
                            .followSslRedirects(true)
                            .connectTimeout(Const.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(Const.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(Const.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                            .cookieJar(new MemoryCookieJar())
                            .retryOnConnectionFailure(true)
                            .addNetworkInterceptor(new ContentTypeInterceptor())
                            .build();

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Const.HTTP_HOST)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build();
                    API = retrofit.create(HttpApi.class);
                }
            }
        }
        return API;
    }


}
