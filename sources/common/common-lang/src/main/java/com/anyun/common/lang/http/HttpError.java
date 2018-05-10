package com.anyun.common.lang.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public class HttpError {
    private int code = 0;
    private String message = "";
    private Map<String, List<String>> headers = new HashMap<>();

    public HttpError() {
    }

    public HttpError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpError(int code, String message, Map<String, List<String>> headers) {
        this.code = code;
        this.message = message;
        this.headers = headers;
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

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }
}
