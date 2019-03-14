package com.minapp.android.sdk.database.query;

import android.text.TextUtils;

enum Operator {

    EQ("$eq"),                 // 等于
    NE("$ne"),                 // 不等于
    LT("$lt"),                 // 小于
    LTE("$lte"),               // 小于等于
    GT("$gt"),                 // 大于
    GTE("$gte"),               // 大于等于
    CONTAINS("$contains"),     // 包含任意一个值
    NIN("$nin"),               // 不包含任意一个数组值
    IN("$in"),                 // 包含任意一个数组值
    IS_NULL("$isnull"),        // 是否为 NULL
    RANGE("$range");           // 包含数组值区间的值

    final String value;

    Operator(String value) {
        this.value = value;
    }

    public static Operator from(String op) {
        if (TextUtils.equals(op, EQ.value)) {
            return EQ;
        } else if (TextUtils.equals(op, NE.value)) {
            return NE;
        } else if (TextUtils.equals(op, LT.value)) {
            return LT;
        } else if (TextUtils.equals(op, LTE.value)) {
            return LTE;
        } else if (TextUtils.equals(op, GT.value)) {
            return GT;
        } else if (TextUtils.equals(op, GTE.value)) {
            return GTE;
        } else if (TextUtils.equals(op, CONTAINS.value)) {
            return CONTAINS;
        } else if (TextUtils.equals(op, NIN.value)) {
            return NIN;
        } else if (TextUtils.equals(op, IN.value)) {
            return IN;
        } else if (TextUtils.equals(op, IS_NULL.value)) {
            return IS_NULL;
        } else if (TextUtils.equals(op, RANGE.value)) {
            return RANGE;
        } else {
            throw new IllegalArgumentException(String.format("can't recognize operator [%s]", op));
        }
    }
}
