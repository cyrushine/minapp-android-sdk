package com.minapp.android.sdk;

public abstract class Const {

    public static final String TAG = "minapp-android-sdk";
    public static final String HTTP_HOST = "https://cloud.minapp.com/";
    public static final long HTTP_TIMEOUT = 10 * 1000;                              // http 读、写、连接的超时设置，单位毫秒
    public static final String AUTH_HEADER_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_KEY = "Authorization";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String MEDIA_TYPE_JSON = "application/json";
    public static final String MEDIA_TYPE_FORM = "multipart/form-data";

    public static final String HTTP_POST = "POST";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_GET = "GET";
    public static final String HTTP_DELETE = "DELETE";

}
