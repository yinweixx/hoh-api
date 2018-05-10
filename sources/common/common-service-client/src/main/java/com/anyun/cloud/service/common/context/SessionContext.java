package com.anyun.cloud.service.common.context;

import com.anyun.cloud.service.common.Router;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public interface SessionContext {
    Router getRouter();
    ServiceContext getServiceContext();
}
