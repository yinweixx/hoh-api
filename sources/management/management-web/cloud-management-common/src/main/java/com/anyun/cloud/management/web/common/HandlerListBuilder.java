package com.anyun.cloud.management.web.common;

import org.eclipse.jetty.server.handler.HandlerList;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public interface HandlerListBuilder {
    HandlerList build() throws Exception;
}
