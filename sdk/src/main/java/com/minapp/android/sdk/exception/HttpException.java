package com.minapp.android.sdk.exception;

public class HttpException extends SdkException {

    private final int code;
    private final String errorMsg;

    public HttpException(int code) {
        this(code, null, null);
    }

    public HttpException(int code, String errorMsg) {
        this(code, errorMsg, null);
    }

    public HttpException(int code, String errorMsg, Throwable cause) {
        super(String.format("status code %s\n%s", code, errorMsg), cause);
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
