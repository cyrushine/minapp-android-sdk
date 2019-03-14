package com.minapp.android.sdk.exception;

import android.annotation.TargetApi;

public class SdkException extends Exception {

    public SdkException() {
    }

    public SdkException(String message) {
        super(message);
    }

    public SdkException(String message, Throwable cause) {
        super(message, cause);
    }

    public SdkException(Throwable cause) {
        super(cause);
    }
}
