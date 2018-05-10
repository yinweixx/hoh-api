package com.anyun.common.service.context;

import com.anyun.cloud.service.common.Router;
import com.anyun.cloud.service.common.context.ServiceContext;
import com.anyun.cloud.service.common.context.SessionContext;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class DefaultSessionContext implements SessionContext {
    private Router router;
    private ServiceContext serviceContext;

    public DefaultSessionContext(ServiceContext context, Router router) {
        this.serviceContext = context;
        this.router = router;
    }

    @Override
    public Router getRouter() {
        return router;
    }

    @Override
    public ServiceContext getServiceContext() {
        return serviceContext;
    }
}
