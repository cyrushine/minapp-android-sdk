package com.minapp.android.sdk.database;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.database.query.Result;

public class TableObject {

    private Long tableId;


    public TableObject(long tableId) {
        this.tableId = tableId;
    }

    /**
     * 通过此 api 创建一条新记录
     * @return
     */
    public RecordObject createRecord() {
        return new RecordObject(this);
    }

    /**
     * 通过此 api 获取一条记录
     * @return
     */
    public RecordObject fetchRecord(String recordId) throws Exception {
        return Database.fetch(this, recordId);
    }

    public void fetchRecordInBackground(final String recordId, final Callback callback) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    RecordObject record = fetchRecord(recordId);
                    if (callback != null) {
                        callback.onSuccess(record);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFailure(null, e);
                    }
                }
            }
        });
    }

    /**
     * 查询
     * @param query
     * @return
     * @throws Exception
     */
    public Result query(Query query) throws Exception {
        return Database.query(this, query);
    }

    public void queryInBackground(final Query query, final QueryCallback callback) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Result result = Database.query(TableObject.this, query);
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFailure(TableObject.this, e);
                    }
                }
            }
        });
    }

    Long getTableId() {
        return tableId;
    }

    @Override
    public boolean equals( Object obj) {
        if (obj instanceof TableObject) {
            TableObject other = (TableObject) obj;
            return this.tableId == other.tableId;
        }
        return false;
    }

}
