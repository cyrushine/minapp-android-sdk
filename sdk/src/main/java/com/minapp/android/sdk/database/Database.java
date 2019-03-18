package com.minapp.android.sdk.database;

import android.support.annotation.NonNull;
import com.google.gson.JsonObject;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.PagedListResponse;
import com.minapp.android.sdk.database.query.*;
import com.minapp.android.sdk.util.Util;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public abstract class Database {

    /**
     * 新增 or 更新
     * @param record
     */
    static void save(RecordObject record) throws Exception {
        if (record != null) {

            // 新增
            if (record.id() == null) {
                Response<JsonObject> response = Global.httpApi().saveRecord(record.tableId(), record.toJsonObject()).execute();
                record.updateByServer(response.body());

            } else {

                // 更新
                Response<JsonObject> response = Global.httpApi().updateRecord(
                        record.tableId(), record.id(), record.toJsonObject()).execute();
                record.updateByServer(response.body());
            }
        }
    }

    /**
     * 删除
     * @param record
     * @throws Exception
     */
    static void delete(RecordObject record) throws Exception {
        if (record != null && record.id() != null) {
            Global.httpApi().deleteRecord(record.tableId(), record.id()).execute();
            record.updateByServer(null);
        }
    }

    /**
     * 获取数据
     * @return
     * @throws Exception
     */
    static @NonNull RecordObject fetch(@NonNull TableObject table, String recordId) throws Exception {
        Util.assetNotNull(table);
        Response<JsonObject> response = Global.httpApi().fetchRecord(table.getTableId(), recordId).execute();
        RecordObject record = table.createRecord();
        record.updateByServer(response.body());
        return record;
    }


    /**
     * 查询
     * @param table
     * @param query
     * @return
     * @throws Exception
     */
    static Result query(TableObject table, Query query) throws Exception {
        Result result = new Result();
        if (table != null) {
            String where = query != null ? query.getWhereJson() : null;
            String orderBy = query != null ? query.getOrderBy() : null;
            Long limit = query != null ? query.getLimit() : null;
            Long offset = query != null ? query.getOffset() : null;
            PagedListResponse<JsonObject> body = Global.httpApi().queryRecord(table.getTableId(), where, orderBy, limit, offset).execute().body();

            PagedListResponse.Meta meta = body.getMeta();
            if (meta != null) {
                result.setNext(meta.getNext());
                result.setPrevious(meta.getPrevious());
                result.setLimit(meta.getLimit());
                result.setOffset(meta.getOffset());
            }

            List<JsonObject> objects = body.getObjects();
            if (objects != null) {
                List<RecordObject> records = new ArrayList<>(objects.size());
                result.setRecords(records);

                for (JsonObject object : objects) {
                    RecordObject record = new RecordObject(table);
                    record.updateByServer(object);
                    records.add(record);
                }
            }

        }
        return result;
    }


}
