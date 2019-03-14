package com.minapp.android.sdk;

import com.google.gson.JsonObject;
import com.minapp.android.sdk.auth.*;
import com.minapp.android.sdk.database.query.QueryResponse;
import retrofit2.Call;
import retrofit2.http.*;


/**
 * cloud.minapp.com 提供的 http api
 */
public interface HttpApi {

    /**
     * 登录认证相关 api
     *
     * 知晓云开放 API 授权通过 Access Token 作为接口调用的凭证，在对开放 API 发起请求时，均需要在 HTTP Header 加入以下授权参数：
     * Authorization: Bearer <Access Token>
     *
     *                    授权流程
     *
     *   +--------+      ID/Secrct      +--------+
     *   |        | +-----------------> |        |
     *   |        |                     |        |
     *   |        |         Code        |        |
     *   |        | <-----------------+ |        |
     *   | Client |                     | 知晓云  |
     *   |        |         Code        |        |
     *   |        | +-----------------> |        |
     *   |        |                     |        |
     *   |        |    Access Token     |        |
     *   |        | <-----------------+ |        |
     *   +--------+                     +--------+
     *
     */

    /**
     * 获取 code
     * @param body
     * @return
     */
    @POST("api/oauth2/hydrogen/openapi/authorize/")
    @Headers("Content-Type: application/json")
    Call<CodeResponse> code(@Body CodeRequest body);


    /**
     * 获取 access token
     * @param body
     * @return
     */
    @POST("api/oauth2/access_token/")
    @Headers("Content-Type: application/json")
    Call<AccessTokenResponse> accessToken(@Body AccessTokenRequest body);

    /**
     * 写入一条记录
     * @param tableId
     * @return
     */
    @POST("oserve/v1/table/{table_id}/record/")
    @Headers("Content-Type: application/json")
    CheckedCall<JsonObject> saveRecord(
            @Path("table_id") long tableId,
            @Body JsonObject body
    );


    /**
     * 更新记录
     * @param tableId
     * @param recordId
     * @param body
     * @return
     */
    @PUT("oserve/v1/table/{table_id}/record/{record_id}/")
    @Headers("Content-Type: application/json")
    CheckedCall<JsonObject> updateRecord(
            @Path("table_id") long tableId,
            @Path("record_id") String recordId,
            @Body JsonObject body
    );

    /**
     * 删除记录
     * @param tableId
     * @param recordId
     * @return
     */
    @DELETE("oserve/v1/table/{table_id}/record/{record_id}/")
    CheckedCall<Void> deleteRecord(
            @Path("table_id") long tableId,
            @Path("record_id") String recordId
    );

    /**
     * 获取一条记录
     * @param tableId
     * @param recordId
     * @return
     */
    @GET("oserve/v1/table/{table_id}/record/{record_id}/")
    CheckedCall<JsonObject> fetchRecord(
            @Path("table_id") long tableId,
            @Path("record_id") String recordId
    );


    /**
     * 查询记录
     * @param tableId
     * @return
     */
    @GET("oserve/v1/table/{table_id}/record/")
    CheckedCall<QueryResponse> queryRecord(
            @Path("table_id") long tableId,
            @Query("where") String where,
            @Query("order_by") String orderBy,
            @Query("limit") Long limit,
            @Query("offset") Long offset
    );

}
