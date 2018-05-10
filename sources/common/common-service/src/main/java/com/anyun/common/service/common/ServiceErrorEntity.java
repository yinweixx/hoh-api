package com.anyun.common.service.common;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class ServiceErrorEntity {
    private String result = "error";
    private int code = 505;
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServiceErrorEntity{" +
                "result='" + result + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
