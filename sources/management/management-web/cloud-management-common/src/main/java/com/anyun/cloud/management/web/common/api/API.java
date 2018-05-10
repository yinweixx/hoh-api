package com.anyun.cloud.management.web.common.api;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/07/2017
 */
public class API {
    private String path;
    private String method;
    private String contentType;
    private String[] consumes = new String[]{};
    private Map<String,List<String>> queryString;
    private HttpServletRequest request;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String[] getConsumes() {
        return consumes;
    }

    public void setConsumes(String[] consumes) {
        this.consumes = consumes;
    }

    public Map<String, List<String>> getQueryString() {
        return queryString;
    }

    public void setQueryString(Map<String, List<String>> queryString) {
        this.queryString = queryString;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
