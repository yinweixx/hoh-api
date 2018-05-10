package com.anyun.common.service.context;

import com.anyun.cloud.service.common.context.ServiceContext;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.service.common.ServiceCache;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class DefauleServiceContext implements ServiceContext {
    @Override
    public String getDeviceId() {
        return InjectorsBuilder.getBuilder().getInstanceByType(ServiceCache.class).getDeviceId();
    }
}
