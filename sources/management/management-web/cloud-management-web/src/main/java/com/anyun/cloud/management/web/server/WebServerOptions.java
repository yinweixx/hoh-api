package com.anyun.cloud.management.web.server;

import com.anyun.common.lang.options.AbstractApplicationOptions;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public class WebServerOptions extends AbstractApplicationOptions {
    public static final String OPT_HTTP_PORT = "http_port";
    public static final String WEB_DEPLOY_DIR = "webapp_deploy";
    public static final String TEMPLATE_CACHE_TTL = "template_cache_ttl";
    public static final String WEB_GIT_URL = "web_git_url";

    public WebServerOptions(String[] args) {
        super(args);
    }

    @Override
    protected void buildOptions() {
        getOptions().addOption(OPT_HTTP_PORT, true, "Http server port");
        getOptions().addOption(WEB_DEPLOY_DIR, true, "Web app deploy directory");
        getOptions().addOption(TEMPLATE_CACHE_TTL, true, "Template cache TTL");
        getOptions().addOption(WEB_GIT_URL, true, "Web app git url");
    }
}
