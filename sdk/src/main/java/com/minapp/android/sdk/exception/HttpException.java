package com.minapp.android.sdk.exception;

public class HttpException extends SdkException {

    private final int code;

    public HttpException(int code) {
        super(String.format("http code %s", code));
        this.code = code;
    }

    public HttpException(int code, Throwable cause) {
        super(String.format("http code %s", code), cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
