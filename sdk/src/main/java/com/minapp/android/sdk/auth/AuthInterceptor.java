package com.minapp.android.sdk.auth;

import com.minapp.android.sdk.Const;
import okhttp3.Interceptor;
import okhttp3.Request;

import java.io.IOException;

/**
 * 在 okhttp request 中加入认证头
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String clientId = Auth.clientId();
        if (clientId != null) {
            builder.header(Const.AUTH_HEADER_CLIENT_ID, clientId);
        }
        String token = Auth.token();
        if (token != null) {
            builder.header(Const.AUTH_HEADER_AUTH, Const.AUTH_HEADER_AUTH_PREFIX + token);
        }
        return chain.proceed(builder.build());
    }
}
