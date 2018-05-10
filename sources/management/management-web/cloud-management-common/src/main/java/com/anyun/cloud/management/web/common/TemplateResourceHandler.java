package com.anyun.cloud.management.web.common;

import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public class TemplateResourceHandler extends ResourceHandler {
    public TemplateResourceHandler() {
        super();
        setDirectoriesListed(false);
        setResourceBase(".");
    }
}
