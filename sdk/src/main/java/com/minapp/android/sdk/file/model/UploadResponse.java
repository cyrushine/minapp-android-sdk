package com.minapp.android.sdk.file.model;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    /**
     * image-type : GIF
     * image-frames : 8
     * image-height : 8
     * code : 200
     * file_size : 329
     * image-width : 8
     * url : 1eMQRlkJwhgaMiCg.gif
     * time : 1512531154
     * message : ok
     * mimetype : image/gif
     */

    @SerializedName("image-type")
    private String imageType;

    @SerializedName("image-frames")
    private Long imageFrames;

    @SerializedName("image-height")
    private Long imageHeight;

    @SerializedName("code")
    private Long code;

    @SerializedName("file_size")
    private Long fileSize;

    @SerializedName("image-width")
    private Long imageWidth;

    @SerializedName("url")
    private String url;

    @SerializedName("time")
    private Long time;

    @SerializedName("message")
    private String message;

    @SerializedName("mimetype")
    private String mimeType;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Long getImageFrames() {
        return imageFrames;
    }

    public void setImageFrames(Long imageFrames) {
        this.imageFrames = imageFrames;
    }

    public Long getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Long imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Long imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
