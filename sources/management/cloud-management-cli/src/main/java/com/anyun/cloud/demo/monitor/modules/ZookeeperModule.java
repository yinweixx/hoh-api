package com.anyun.cloud.demo.monitor.modules;

import com.anyun.cloud.demo.common.etcd.spi.EtcdExtendService;
import com.anyun.cloud.demo.common.etcd.spi.impl.EtcdExtenedServiceImpl;
import com.anyun.cloud.demo.common.registry.utils.DefaultZookeeperClient;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.AbstractModule;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 07/06/2017
 */
public class ZookeeperModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EtcdExtendService.class).to(EtcdExtenedServiceImpl.class);
        bind(ZookeeperClient.class).to(DefaultZookeeperClient.class);
    }
}
