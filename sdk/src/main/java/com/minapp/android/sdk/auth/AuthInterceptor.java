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
        String accessToken = Auth.accessToken();
        Request request = chain.request();
        if (accessToken != null) {
            request = request
                    .newBuilder()
                    .addHeader(Const.AUTH_HEADER_KEY, Const.AUTH_HEADER_PREFIX + accessToken)
                    .build();
        }
        return chain.proceed(request);
    }
}
