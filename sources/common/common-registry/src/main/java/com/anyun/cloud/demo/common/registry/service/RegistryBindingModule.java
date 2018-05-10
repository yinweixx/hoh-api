package com.anyun.cloud.demo.common.registry.service;

import com.anyun.cloud.demo.common.registry.service.impl.NodeRegistServiceImpl;
import com.anyun.cloud.demo.common.registry.utils.DefaultZookeeperClient;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class RegistryBindingModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryBindingModule.class);

    @Override
    protected void configure() {
        bind(ZookeeperClient.class).to(DefaultZookeeperClient.class);
        LOGGER.info("Bind zookeeper client implement to: ", DefaultZookeeperClient.class);

        bind(NodeRegistService.class).to(NodeRegistServiceImpl.class);
        LOGGER.info("Bind node regist service to: {}", NodeRegistServiceImpl.class);
    }
}
