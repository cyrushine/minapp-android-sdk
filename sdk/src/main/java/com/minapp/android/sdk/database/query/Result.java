package com.minapp.android.sdk.database.query;

import com.google.gson.JsonObject;
import com.minapp.android.sdk.database.RecordObject;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private String next;
    private String previous;
    private long totalCount;
    private long limit;
    private long offset;
    private List<RecordObject> records;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public List<RecordObject> getRecords() {
        return records;
    }

    public void setRecords(List<RecordObject> records) {
        this.records = records;
    }
}
