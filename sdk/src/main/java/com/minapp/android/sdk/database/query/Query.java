package com.minapp.android.sdk.database.query;

import android.support.annotation.NonNull;
import com.minapp.android.sdk.Global;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Query {

    ConditionNode where = new ConditionNode();
    String orderBy;
    Long limit;
    Long offset;

    Query and(Operator op, String lvalue, Object rvalue) {
        where.addCondition(new Condition(op, lvalue, rvalue));
        return this;
    }


    /*************************  and, or  ******************************/


    public static @NonNull Query and(Query lvalue, Query rvalue) {
        if (lvalue == null && rvalue == null) {
            return new Query();
        }

        if (lvalue == null) {
            return rvalue;
        }

        if (rvalue == null) {
            return lvalue;
        }

        Query newQuery = new Query();
        newQuery.where = ConditionNode.and(lvalue.where, rvalue.where);
        return newQuery;
    }

    public static @NonNull Query or(Query lvalue, Query rvalue) {
        if (lvalue == null && rvalue == null) {
            return new Query();
        }

        if (lvalue == null) {
            return rvalue;
        }

        if (rvalue == null) {
            return lvalue;
        }

        Query newQuery = new Query();
        newQuery.where = ConditionNode.or(lvalue.where, rvalue.where);
        return newQuery;
    }


    /*************************  common  ******************************/


    public Query isNull(String lvalue) {
        return and(Operator.IS_NULL, lvalue, null);
    }

    public Query orderBy(String column) {
        this.orderBy = column;
        return this;
    }

    public Query limit(long limit) {
        this.limit = limit;
        return this;
    }

    public Query offset(long offset) {
        this.offset = offset;
        return this;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public Long getLimit() {
        return limit;
    }

    public Long getOffset() {
        return offset;
    }

    public String getWhereJson() {
        return Global.gson().toJson(where);
    }

    /*************************  string  ******************************/


    public Query eq(String lvalue, String rvalue) {
        return and(Operator.EQ, lvalue, rvalue);
    }

    public Query in(String lvalue, String rvalue) {
        return and(Operator.IN, lvalue, rvalue);
    }

    public Query nin(String lvalue, String rvalue) {
        return and(Operator.NIN, lvalue, rvalue);
    }

    public Query ne(String lvalue, String rvalue) {
        return and(Operator.NE, lvalue, rvalue);
    }

    public Query contains(String lvalue, String rvalue) {
        return and(Operator.CONTAINS, lvalue, rvalue);
    }


    /*************************  number  ******************************/


    public Query eq(String lvalue, Number rvalue) {
        return and(Operator.EQ, lvalue, rvalue);
    }

    public Query ne(String lvalue, Number rvalue) {
        return and(Operator.NE, lvalue, rvalue);
    }

    public Query lt(String lvalue, Number rvalue) {
        return and(Operator.LT, lvalue, rvalue);
    }

    public Query lte(String lvalue, Number rvalue) {
        return and(Operator.LTE, lvalue, rvalue);
    }

    public Query gt(String lvalue, Number rvalue) {
        return and(Operator.GT, lvalue, rvalue);
    }

    public Query gte(String lvalue, Number rvalue) {
        return and(Operator.GTE, lvalue, rvalue);
    }

    public Query in(String lvalue, Number rvalue) {
        return and(Operator.IN, lvalue, rvalue);
    }

    public Query nin(String lvalue, Number rvalue) {
        return and(Operator.NIN, lvalue, rvalue);
    }


    /*************************  boolean  ******************************/


    public Query eq(String lvalue, boolean rvalue) {
        return and(Operator.EQ, lvalue, rvalue);
    }



}
