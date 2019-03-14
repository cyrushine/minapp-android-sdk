package com.minapp.android.sdk.exception;

public class AuthException extends SdkException {
    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(int statusCode) {
        super(String.format("http status code %s", statusCode));
    }
}
