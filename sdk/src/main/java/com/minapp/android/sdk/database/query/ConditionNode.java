package com.minapp.android.sdk.database.query;

import com.google.gson.*;
import com.minapp.android.sdk.util.Util;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ConditionNode {

    // 数据段
    private Relation relation = Relation.AND;
    private Condition condition;

    // 子树
    private List<ConditionNode> children = new LinkedList<>();

    ConditionNode() {}

    ConditionNode(ConditionNode orign) {
        Util.assetNotNull(orign);
        this.relation = orign.relation;
        this.condition = orign.condition;
        this.children.addAll(orign.children);
    }

    private ConditionNode(Condition condition) {
        this.condition = condition;
    }

    void addCondition(Condition condition) {
        if (condition != null) {
            if (this.condition == null) {
                this.condition = condition;
            } else {
                this.children.add(new ConditionNode(this.condition));
                this.condition = null;
                this.children.add(new ConditionNode(condition));
            }
        }
    }

    void reset() {
        relation = Relation.AND;
        condition = null;
        children.clear();
    }


    static ConditionNode or(ConditionNode lvalue, ConditionNode rvalue) {
        ConditionNode newNode = new ConditionNode();
        newNode.relation = Relation.OR;
        newNode.children.add(new ConditionNode(lvalue));
        newNode.children.add(new ConditionNode(rvalue));
        lvalue.reset();
        rvalue.reset();
        return newNode;
    }

    static ConditionNode and(ConditionNode lvalue, ConditionNode rvalue) {
        ConditionNode newNode = new ConditionNode();
        newNode.children.add(new ConditionNode(lvalue));
        newNode.children.add(new ConditionNode(rvalue));
        lvalue.reset();
        rvalue.reset();
        return newNode;
    }


    public static class Serializer implements JsonSerializer<ConditionNode> {
        @Override
        public JsonElement serialize(ConditionNode src, Type typeOfSrc, JsonSerializationContext context) {
            if (src.condition != null) {
                return context.serialize(src.condition);
            }

            JsonObject root = new JsonObject();
            if (src.relation != null) {
                JsonArray array = new JsonArray();
                for (ConditionNode child : src.children) {
                    array.add(context.serialize(child));
                }
                root.add(src.relation.value, array);
            }
            return root;
        }
    }
}
