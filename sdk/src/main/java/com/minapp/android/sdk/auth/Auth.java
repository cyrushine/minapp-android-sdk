package com.minapp.android.sdk.auth;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.HttpApi;
import com.minapp.android.sdk.auth.*;
import com.minapp.android.sdk.exception.AuthException;
import com.minapp.android.sdk.exception.HttpException;
import com.minapp.android.sdk.util.ContentTypeInterceptor;
import com.minapp.android.sdk.util.MemoryCookieJar;
import okhttp3.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class Auth {

    private static String CLIENT_ID;
    private static String CLIENT_SECRET;
    private static final MemoryCookieJar COOKIE_JAR = new MemoryCookieJar();
    private static HttpApi API;
    private static final Object API_LOCK = new Object();
    private static AccessTokenResponse AUTH_INFO;


    public static void init(String clientId, String clientSecret) {
        CLIENT_ID = clientId;
        CLIENT_SECRET = clientSecret;
    }

    /**
     * 进行登录认证，得到 access token
     * @throws AuthException
     */
    static void auth() throws AuthException {
        try {
            HttpApi api = httpApi();

            Response<CodeResponse> codeResp = api.code(new CodeRequest(CLIENT_ID, CLIENT_SECRET)).execute();
            if (!codeResp.isSuccessful()) {
                throw new HttpException(codeResp.code());
            }
            CodeResponse code = codeResp.body();
            if (code == null || TextUtils.isEmpty(code.getCode())) {
                throw new Exception("code is empty");
            }

            Response<AccessTokenResponse> tokenResp = api.accessToken(new AccessTokenRequest(CLIENT_ID, CLIENT_SECRET, code.getCode())).execute();
            if (!tokenResp.isSuccessful()) {
                throw new HttpException(tokenResp.code());
            }
            AccessTokenResponse token = tokenResp.body();
            if (token == null || TextUtils.isEmpty(token.getAccessToken())) {
                throw new Exception("access token is empty");
            }
            AUTH_INFO = token;
        } catch (Exception e) {
            throw new AuthException(e);
        } finally {
            COOKIE_JAR.clear();
        }
    }

    static @Nullable String accessToken() {
        return AUTH_INFO != null ? AUTH_INFO.getAccessToken() : null;
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
