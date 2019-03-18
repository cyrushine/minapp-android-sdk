package com.minapp.android.sdk.file;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.PagedList;
import com.minapp.android.sdk.util.PagedListResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Storage {

    static final String PART_AUTHORIZATION = "authorization";
    static final String PART_POLICY = "policy";
    static final String PART_FILE = "file";

    /**
     * 文件上传，分两步：<br />
     * 1. 获取上传文件所需授权凭证和上传地址<br />
     * 2. 使用上一步获取的授权凭证和上传地址，进行文件上传
     */
    public static CloudFile uploadFile(String filename, byte[] data) throws Exception {
        UploadMetaRequest body = new UploadMetaRequest();
        body.setFileName(filename);
        UploadMetaResponse meta = Global.httpApi().getUploadMeta(body).execute().body();

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PART_AUTHORIZATION, meta.getAuthorization())
                .addFormDataPart(PART_POLICY, meta.getPolicy())
                .addFormDataPart(PART_FILE, filename, RequestBody.create(null, data))
                .build();
        Response<UploadResponse> response = Global.httpApi().uploadFile(
                meta.getUploadUrl(),
                multipartBody
        ).execute();
        return file(meta.getId());
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
        PagedListResponse<CloudFile> files = new PagedListResponse();
        files.setMeta(list.getMeta());
        if (list.getObjects() != null) {
            List<CloudFile> objects = new ArrayList<>(list.getObjects().size());
            for (FileMetaResponse item : list.getObjects()) {
                objects.add(new CloudFile(item));
            }
            files.setObjects(objects);
        }
        return new PagedList<>(files);
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
}
