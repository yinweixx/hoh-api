package com.anyun.example.service.impl;

import com.anyun.cloud.service.common.CloudService;
import com.anyun.cloud.service.common.Router;
import com.anyun.cloud.service.common.Service;
import com.anyun.cloud.service.common.context.ServiceContext;
import com.anyun.cloud.service.common.context.SessionContext;
import com.anyun.cloud.service.common.exchange.Exchange;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
@CloudService(servicePath = "/app1/api/v1_0_0/api2")
public class ExampleService2 implements Service<String> {

    @Override
    public String onExchange(Exchange exchange) throws Exception {
        String resourceId = "/np/app1/service1";
        SessionContext sessionContext = exchange.getSessionContext();
        Router router = sessionContext.getRouter();
        String result = router.route(resourceId, null, null, String.class);
        return result;
    }
}
