package com.minapp.android.sdk.util;

import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;

import java.io.IOException;

public class PrintInterceptor implements Interceptor {

    private static final String TAG = "PrintInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response resp = chain.proceed(chain.request());
        printResponsd(resp);
        return resp;
    }

    private void printResponsd(Response resp) {
        Request req = resp.request();
        StringBuilder sb = new StringBuilder(" \n");

        sb.append(req.method()).append(" ").append(req.url());
        if (req.headers().size() > 0) {
            sb.append("\nheaders\n").append(req.headers());
        }
        if (req.body() != null) {
            Buffer buffer = new Buffer();
            try {
                req.body().writeTo(buffer);
                String body = buffer.readByteString().utf8();
                if (!TextUtils.isEmpty(body)) {
                    sb.append("\nbody\n").append(body).append("\n");
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            } finally {
                buffer.clear();
            }
        }

        sb.append("\n").append(resp.code());
        if (resp.headers().size() > 0) {
            sb.append("\nheaders\n").append(resp.headers());
        }
        if (resp.body() != null) {
            try {
                String str = Util.readString(resp.body().byteStream());
                if (!TextUtils.isEmpty(str)) {
                    sb.append("\nbody\n").append(str).append("\n");
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        Log.d(TAG, sb.toString());
    }
}
