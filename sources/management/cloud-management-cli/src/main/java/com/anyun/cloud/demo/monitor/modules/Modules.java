package com.anyun.cloud.demo.monitor.modules;

import com.anyun.cloud.demo.monitor.message.NatsClient;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.zookeeper.ZookeeperClient;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public class Modules {
    public static void init(String[] args) throws Exception {
        InjectorsBuilder builder = InjectorsBuilder.getBuilder();
        builder.build(
                new ShellModule(),
                new EtcdModule(),
                new NatsModule(),
                new ZookeeperModule()
        );
        testingConnections();
        InjectorsBuilder.getBuilder().getInstanceByType(ZookeeperClient.class).start();
    }

    private static void testingConnections() {
        InjectorsBuilder.getBuilder().getInstanceByType(NatsClient.class);
    }
}
