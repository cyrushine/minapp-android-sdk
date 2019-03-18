package com.minapp.android.sdk.file;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.exception.SdkException;
import com.minapp.android.sdk.file.category.CategoryInfo;
import com.minapp.android.sdk.file.category.CreateCategoryBody;
import com.minapp.android.sdk.file.category.UpdateCategoryBody;
import com.minapp.android.sdk.file.model.FileMetaResponse;
import com.minapp.android.sdk.file.model.UploadMetaBody;
import com.minapp.android.sdk.file.model.UploadMetaResponse;
import com.minapp.android.sdk.file.model.UploadResponse;
import com.minapp.android.sdk.util.Function;
import com.minapp.android.sdk.util.PagedList;
import com.minapp.android.sdk.util.PagedListResponse;
import com.minapp.android.sdk.util.Util;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public abstract class Storage {

    static final String PART_AUTHORIZATION = "authorization";
    static final String PART_POLICY = "policy";
    static final String PART_FILE = "file";
    static final int FILE_CHECKING_MILLIS = 500;
    public static final int FILE_CHECKING_MAX = 10;

    /**
     * 文件上传，分两步：<br />
     * 1. 获取上传文件所需授权凭证和上传地址<br />
     * 2. 使用上一步获取的授权凭证和上传地址，进行文件上传
     */
    public static CloudFile uploadFile(String filename, byte[] data) throws Exception {
        return uploadFile(filename, null, data);
    }

    /**
     * 文件上传，分两步：<br />
     * 1. 获取上传文件所需授权凭证和上传地址<br />
     * 2. 使用上一步获取的授权凭证和上传地址，进行文件上传
     */
    public static CloudFile uploadFile(String filename, String categoryId, byte[] data) throws Exception {
        UploadMetaBody body = new UploadMetaBody();
        body.setFileName(filename);
        body.setCategoryId(categoryId);
        UploadMetaResponse meta = Global.httpApi().getUploadMeta(body).execute().body();

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PART_AUTHORIZATION, meta.getAuthorization())
                .addFormDataPart(PART_POLICY, meta.getPolicy())
                .addFormDataPart(PART_FILE, filename, RequestBody.create(null, data))
                .build();
        Global.httpApi().uploadFile(meta.getUploadUrl(), multipartBody).execute();

        CloudFile uploaded = null;
        int maxLoop = FILE_CHECKING_MAX;
        synchronized (meta) {
            while (maxLoop > 0) {
                uploaded = file(meta.getId());
                if (uploaded.isUploadSuccess()) {
                    return uploaded;
                } else {
                    maxLoop--;
                    meta.wait(FILE_CHECKING_MILLIS);
                }
            }
        }
        return uploaded;
    }


    /**
     * 文件信息
     * @param id
     * @return
     * @throws Exception
     */
    public static CloudFile file(String id) throws Exception {
        return new CloudFile(Global.httpApi().file(id).execute().body());
    }


    /**
     * 查询文件列表
     * @param orderBy
     * @param limit
     * @param offset
     * @return
     * @throws Exception
     */
    public static PagedList<CloudFile> files(String orderBy, Long limit, Long offset) throws Exception {
        PagedListResponse<FileMetaResponse> list = Global.httpApi().files(orderBy, limit, offset).execute().body();
        return new PagedList<>(Util.transform(list, new Function<FileMetaResponse, CloudFile>() {
            @Override
            public CloudFile on(FileMetaResponse meta) {
                return new CloudFile(meta);
            }
        }));
    }

    /**
     * 批量删除文件
     * @param ids
     * @throws Exception
     */
    public static void deleteFiles(List<String> ids) throws Exception {
        if (ids == null || ids.size() == 0) {
            return;
        }

        if (ids.size() == 1) {
            Global.httpApi().deleteFile(ids.get(0)).execute();
        } else {
            Global.httpApi().deleteFiles(ids).execute();
        }
    }


    /**
     * 创建分类
     * @param name
     * @return
     * @throws Exception
     */
    public static Category createCategory(String name) throws Exception {
        return new Category(Global.httpApi().createCategory(new CreateCategoryBody(name)).execute().body());
    }

    /**
     * 获取分类
     * @param id
     * @return
     * @throws Exception
     */
    public static Category category(String id) throws Exception {
        return new Category(Global.httpApi().category(id).execute().body());
    }

    /**
     * 列表查询分类
     * @param orderBy
     * @param limit
     * @param offset
     * @return
     * @throws Exception
     */
    public static PagedList<Category> categories(String orderBy, Long limit, Long offset) throws Exception {
        PagedListResponse<CategoryInfo> list = Global.httpApi().categories(orderBy, limit, offset).execute().body();
        return new PagedList<Category>(Util.transform(list, new Function<CategoryInfo, Category>() {
            @Override
            public Category on(CategoryInfo info) {
                return new Category(info);
            }
        }));
    }

    /**
     * 更新分类
     * @param id
     * @param name
     * @return
     * @throws Exception
     */
    public static Category updateCategory(String id, String name) throws Exception {
        return new Category(Global.httpApi().updateCategory(id, new UpdateCategoryBody(name)).execute().body());
    }

    /**
     * 删除分类
     * @param id
     * @throws Exception
     */
    public static void deleteCategory(String id) throws Exception {
        Global.httpApi().deleteCategory(id).execute();
    }

}
