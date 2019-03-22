package com.minapp.android.sdk;

import com.google.gson.JsonObject;
import com.minapp.android.sdk.auth.*;
import com.minapp.android.sdk.auth.model.SignUpInByEmailReq;
import com.minapp.android.sdk.auth.model.SignUpInByUsernameReq;
import com.minapp.android.sdk.auth.model.SignUpInResp;
import com.minapp.android.sdk.file.*;
import com.minapp.android.sdk.file.category.CategoryInfo;
import com.minapp.android.sdk.file.category.CreateCategoryBody;
import com.minapp.android.sdk.file.category.UpdateCategoryBody;
import com.minapp.android.sdk.file.model.FileMetaResponse;
import com.minapp.android.sdk.file.model.UploadMetaBody;
import com.minapp.android.sdk.file.model.UploadMetaResponse;
import com.minapp.android.sdk.file.model.UploadResponse;
import com.minapp.android.sdk.util.PagedListResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Collection;


/**
 * cloud.minapp.com 提供的 http api
 */
public interface HttpApi {


    /********************************* auth api ****************************************/

    /**
     * 通过邮箱注册
     * @param body
     * @return
     */
    @POST("hserve/v2.0/register/email/")
    CheckedCall<SignUpInResp> signUpByEmail(@Body SignUpInByEmailReq body);

    /**
     * 通过用户名注册
     * @param body
     * @return
     */
    @POST("hserve/v2.0/register/username/")
    CheckedCall<SignUpInResp> signUpByUsername(@Body SignUpInByUsernameReq body);

    /**
     * 邮箱登录
     * @param body
     * @return
     */
    @POST("hserve/v2.0/login/email/")
    CheckedCall<SignUpInResp> signInByEmail(@Body SignUpInByEmailReq body);

    /**
     * 用户名登录
     * @param body
     * @return
     */
    @POST("hserve/v2.0/login/username/")
    CheckedCall<SignUpInResp> signInByUsername(@Body SignUpInByUsernameReq body);



    /********************************* Record api ****************************************/


    /**
     * 写入一条记录
     * @param tableId
     * @return
     */
    @POST("hserve/v1/table/{table_id}/record/")
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
    CheckedCall<PagedListResponse<JsonObject>> queryRecord(
            @Path("table_id") long tableId,
            @Query("where") String where,
            @Query("order_by") String orderBy,
            @Query("limit") Long limit,
            @Query("offset") Long offset
    );


    /********************************* File api ****************************************/


    /**
     * 上传文件前现获取到上传相关的必要信息
     * @param body
     * @return
     */
    @POST("oserve/v1/upload/")
    CheckedCall<UploadMetaResponse> getUploadMeta(@Body UploadMetaBody body);

    /**
     * 上传文件
     * @param url {@link UploadMetaResponse#uploadUrl}
     * @param body {@link Storage#uploadFile(String, byte[])}
     */
    @POST
    CheckedCall<UploadResponse> uploadFile(
            @Url String url,
            @Body RequestBody body
    );

    /**
     * 文件信息
     * @param id
     * @return
     */
    @GET("oserve/v1/file/{file_id}/")
    CheckedCall<FileMetaResponse> file(@Path("file_id") String id);


    /**
     * 查询文件列表
     * @param orderBy
     * @param limit
     * @param offset
     * @return
     */
    @GET("oserve/v1/file/")
    CheckedCall<PagedListResponse<FileMetaResponse>> files(
            @Query("order_by") String orderBy,
            @Query("limit") Long limit,
            @Query("offset") Long offset
    );

    /**
     * 删除单个文件
     * @param id
     * @return
     */
    @DELETE("oserve/v1/file/{file_id}/")
    CheckedCall<Void> deleteFile(@Path("file_id") String id);

    /**
     * 批量删除文件
     * @param ids
     * @return
     */
    @DELETE("oserve/v1/file/")
    CheckedCall<Void> deleteFiles(@Query("id__in") Collection<String> ids);


    /********************************* Category api ****************************************/


    /**
     * 创建分类
     * @param body
     * @return
     */
    @POST("oserve/v1/file-category/")
    CheckedCall<CategoryInfo> createCategory(@Body CreateCategoryBody body);

    /**
     * 获取分类信息
     * @param id
     * @return
     */
    @GET("oserve/v1/file-category/{category_id}/")
    CheckedCall<CategoryInfo> category(@Path("category_id") String id);

    /**
     * 分类列表
     * @param orderBy
     * @param limit
     * @param offset
     * @return
     */
    @GET("oserve/v1/file-category/")
    CheckedCall<PagedListResponse<CategoryInfo>> categories(
            @Query("order_by") String orderBy,
            @Query("limit") Long limit,
            @Query("offset") Long offset
    );

    /**
     * 修改分类
     * @param body
     * @return
     */
    @PUT("oserve/v1/file-category/{category_id}/")
    CheckedCall<CategoryInfo> updateCategory(
            @Path("category_id") String id,
            @Body UpdateCategoryBody body
    );

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DELETE("oserve/v1/file-category/{category_id}/")
    CheckedCall<Void> deleteCategory(@Path("category_id") String id);

}
