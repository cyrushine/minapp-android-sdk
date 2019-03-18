package com.minapp.android.sdk.util;

import java.util.List;

public class PagedList<T> {

    private PagedListResponse<T> list;

    public PagedList(PagedListResponse<T> list) {
        this.list = list;
    }

    public List<T> getObjects() {
        try {
            return list.getObjects();
        } catch (Exception e) {
            return null;
        }
    }

    public Long getLimit() {
        try {
            return list.getMeta().getLimit();
        } catch (Exception e) {
            return null;
        }
    }

    public String getNext() {
        try {
            return list.getMeta().getNext();
        } catch (Exception e) {
            return null;
        }
    }

    public Long getOffset() {
        try {
            return list.getMeta().getOffset();
        } catch (Exception e) {
            return null;
        }
    }

    public String getPrevious() {
        try {
            return list.getMeta().getPrevious();
        } catch (Exception e) {
            return null;
        }
    }

    public Long getTotalCount() {
        try {
            return list.getMeta().getTotalCount();
        } catch (Exception e) {
            return null;
        }
    }
}
