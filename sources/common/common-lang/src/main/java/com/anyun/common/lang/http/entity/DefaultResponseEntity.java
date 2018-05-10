package com.anyun.common.lang.http.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public class DefaultResponseEntity<T> implements BaseResponseEntity {

    @SerializedName("code")
    private int code = 200;
    @SerializedName("message")
    private String message = "success";
    @SerializedName("content")
    private T content;

    public DefaultResponseEntity() {
    }

    public DefaultResponseEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
