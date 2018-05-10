package com.anyun.cloud.demo.api.management.module;

import com.anyun.cloud.demo.api.management.core.distributed.management.DistributedManagementService;
import com.anyun.cloud.demo.api.management.core.distributed.management.DistributedManagementServiceImpl;
import com.anyun.cloud.demo.api.management.core.service.ApiManagementService;
import com.anyun.cloud.demo.api.management.core.service.impl.DefaultApiManagementServiceImpl;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class ServiceBindingModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBindingModule.class);

    @Override
    protected void configure() {
        bind(ApiManagementService.class).to(DefaultApiManagementServiceImpl.class);
        LOGGER.info("Bind API management service implement to: {}",
                DefaultApiManagementServiceImpl.class.getCanonicalName());
        bind(DistributedManagementService.class).to(DistributedManagementServiceImpl.class);
        LOGGER.info("Bind distributed management service implement to: {}",
                DistributedManagementServiceImpl.class.getCanonicalName());
    }
}
