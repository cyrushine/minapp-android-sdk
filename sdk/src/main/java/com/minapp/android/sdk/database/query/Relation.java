package com.minapp.android.sdk.database.query;

enum Relation {

    OR("$or"), AND("$and");

    final String value;

    Relation(String value) {
        this.value = value;
    }
}
