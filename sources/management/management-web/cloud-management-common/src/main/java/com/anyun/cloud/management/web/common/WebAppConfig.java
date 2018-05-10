package com.anyun.cloud.management.web.common;

import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public class WebAppConfig {
    private int port = 8080;
    private String workDir = "";
    private WebAppContext context;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public WebAppContext getContext() {
        return context;
    }

    public void setContext(WebAppContext context) {
        this.context = context;
    }

    public String getWorkDir() {
        return workDir;
    }

    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }
}
