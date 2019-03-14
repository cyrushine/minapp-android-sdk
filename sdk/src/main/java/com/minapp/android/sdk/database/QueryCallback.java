package com.minapp.android.sdk.database;

import com.minapp.android.sdk.database.query.Result;

public interface QueryCallback {

    void onSuccess(Result result);

    void onFailure(TableObject table, Exception e);
}
