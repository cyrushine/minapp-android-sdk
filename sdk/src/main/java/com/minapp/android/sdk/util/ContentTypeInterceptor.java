package com.minapp.android.sdk.util;

import android.text.TextUtils;
import android.util.Log;
import com.minapp.android.sdk.Const;
import okhttp3.*;

import java.io.IOException;

public class ContentTypeInterceptor implements Interceptor {

    static final String SUB_TYPE_JSON = "json";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        RequestBody body = request.body();
        MediaType mediaType = body != null ? body.contentType() : null;

        // 设定「Content-Type: application/json」
        // gson convertor 可能会在后面加上「; charset=UTF-8」，这是不符合知晓云 restful api 规范的
        try {
            if (request.url().toString().startsWith(Const.HTTP_HOST)) {
                request = request.newBuilder().header(Const.HEADER_CONTENT_TYPE, Const.MEDIA_TYPE_JSON).build();
            }
        } catch (Exception e) {
            Log.e(Const.TAG, e.getMessage(), e);
        }

        return chain.proceed(request);
    }
}
