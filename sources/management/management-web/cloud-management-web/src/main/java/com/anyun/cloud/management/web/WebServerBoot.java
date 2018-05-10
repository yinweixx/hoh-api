package com.anyun.cloud.management.web;

import com.anyun.cloud.management.web.common.WebServer;
import com.anyun.cloud.management.web.server.WebServerModule;
import com.anyun.cloud.management.web.thymeleaf.ThymesModule;
import com.anyun.common.lang.bean.InjectorsBuilder;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public class WebServerBoot {
    public static void main(String[] args) throws Exception {
        InjectorsBuilder.getBuilder().build(
                new WebServerModule(args),
                new ThymesModule()
        );
        InjectorsBuilder.getBuilder().getInstanceByType(WebServer.class).start();
    }
}
