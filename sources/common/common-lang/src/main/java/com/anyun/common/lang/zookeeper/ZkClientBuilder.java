package com.anyun.common.lang.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class ZkClientBuilder {
    private ZkClientConfig config;
    private CuratorFramework framework;

    public ZkClientBuilder withConfig(ZkClientConfig config) {
        this.config = config;
        return this;
    }

    public CuratorFramework build() {
        return CuratorFrameworkFactory.builder()
                .connectString(config.getConnectionString())
                .retryPolicy(config.getRetryPolicy())
                .connectionTimeoutMs(config.getConnectionTimeoutMs())
                .sessionTimeoutMs(config.getSessionTimeoutMs())
                .build();
    }
}
