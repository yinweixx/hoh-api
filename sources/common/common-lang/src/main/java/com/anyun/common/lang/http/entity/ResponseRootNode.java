package com.anyun.common.lang.http.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public class ResponseRootNode<T extends BaseResponseEntity> {
    @SerializedName("time.server")
    private Date systemDate;
    @SerializedName("time.exec")
    private long execMillisecond;
    @SerializedName("action.name")
    private String action = "";
    @SerializedName("action.result")
    private T result;

    public Date getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(Date systemDate) {
        this.systemDate = systemDate;
    }

    public long getExecMillisecond() {
        return execMillisecond;
    }

    public void setExecMillisecond(long execMillisecond) {
        this.execMillisecond = execMillisecond;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
