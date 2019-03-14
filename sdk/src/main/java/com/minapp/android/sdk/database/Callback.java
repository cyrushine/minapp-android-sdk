package com.minapp.android.sdk.database;

import android.support.annotation.Nullable;

public interface Callback {

    void onSuccess(RecordObject record);

    void onFailure(@Nullable RecordObject record, Exception e);
}
