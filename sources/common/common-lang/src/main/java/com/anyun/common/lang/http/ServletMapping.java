package com.anyun.common.lang.http;

import javax.servlet.Servlet;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 30/05/2017
 */
public class ServletMapping {
    private String apiServletMappingPath = "/*";
    private Class<? extends Servlet> apiProcessServlet;

    public ServletMapping(String apiServletMappingPath, Class<? extends Servlet> apiProcessServlet) {
        this.apiServletMappingPath = apiServletMappingPath;
        this.apiProcessServlet = apiProcessServlet;
    }

    public ServletMapping() {
    }

    public String getApiServletMappingPath() {
        return apiServletMappingPath;
    }

    public void setApiServletMappingPath(String apiServletMappingPath) {
        this.apiServletMappingPath = apiServletMappingPath;
    }

    public Class<? extends Servlet> getApiProcessServlet() {
        return apiProcessServlet;
    }

    public void setApiProcessServlet(Class<? extends Servlet> apiProcessServlet) {
        this.apiProcessServlet = apiProcessServlet;
    }
}
