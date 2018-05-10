package com.anyun.example.service.impl;

import com.anyun.cloud.service.common.CloudService;
import com.anyun.cloud.service.common.context.ServiceContext;
import com.anyun.cloud.service.common.exchange.Exchange;
import com.anyun.cloud.service.common.Service;

import java.util.Date;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
@CloudService(servicePath = "/np/app1/service1", publishApi = false)
public class ExampleNoPublishService1 implements Service<String> {

    @Override
    public String onExchange(Exchange exchange) {
        String deviceId = exchange.getSessionContext().getServiceContext().getDeviceId();
        return "inner_service1@" + deviceId + " time: " + new Date();
    }
}
