package com.anyun.cloud.management.web.server;

import com.anyun.cloud.management.web.common.WebAppConfig;
import com.anyun.cloud.management.web.common.WebAppConfigBuilder;
import com.anyun.common.lang.options.ApplicationOptions;
import com.google.inject.Inject;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public class DefaultWebAppConfigBuilder implements WebAppConfigBuilder {
    private ApplicationOptions options;

    @Inject
    public DefaultWebAppConfigBuilder(ApplicationOptions options) {
        this.options = options;
    }

    public WebAppConfigBuilder withOptions(ApplicationOptions options) {
        this.options = options;
        return this;
    }

    @Override
    public WebAppConfig build() throws Exception {
        WebAppConfig config = new WebAppConfig();
        if (options.getCommandLine().hasOption(WebServerOptions.OPT_HTTP_PORT))
            config.setPort(Integer.valueOf(options.getCommandLine().getOptionValue(WebServerOptions.OPT_HTTP_PORT)));
        if (options.getCommandLine().hasOption(WebServerOptions.WEB_DEPLOY_DIR))
            config.setWorkDir(options.getCommandLine().getOptionValue(WebServerOptions.WEB_DEPLOY_DIR));
        return config;
    }
}
