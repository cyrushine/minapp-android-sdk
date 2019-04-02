package com.minapp.android.sdk.util;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagedListResponse<T> {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("objects")
    private List<T> objects;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public PagedList<T> readonly() {
        return new PagedList<>(this);
    }

    public static class Meta {

        @SerializedName("limit")
        private Long limit;

        @SerializedName("next")
        private String next;

        @SerializedName("offset")
        private Long offset;

        @SerializedName("previous")
        private String previous;

        @SerializedName("total_count")
        private Long totalCount;

        public Long getLimit() {
            return limit;
        }

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public Long getOffset() {
            return offset;
        }

        public void setOffset(Long offset) {
            this.offset = offset;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public Long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Long totalCount) {
            this.totalCount = totalCount;
        }
    }
}
