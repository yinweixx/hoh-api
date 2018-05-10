package com.anyun.cloud.demo.api.node;

import com.anyun.cloud.demo.api.node.module.ComponentsModule;
import com.anyun.cloud.demo.api.node.module.EtcdBindingModule;
import com.anyun.cloud.demo.api.node.module.HttpBindingModule;
import com.anyun.cloud.demo.api.node.module.NatsBindingModule;
import com.anyun.cloud.demo.common.registry.service.RegistryBindingModule;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.http.ApiServer;
import com.anyun.common.lang.msg.NatsClient;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/16
 */
public class ApiNode {
    public static void main(String[] args) throws Exception {
        InjectorsBuilder.getBuilder().build(
                new EtcdBindingModule(),
                new RegistryBindingModule(),
                new HttpBindingModule(),
                new ComponentsModule(),
                new NatsBindingModule());
        NatsClient natsClient = InjectorsBuilder.getBuilder().getInstanceByType(NatsClient.class);
        natsClient.start();
        ApiServer server = InjectorsBuilder.getBuilder().getInstanceByType(ApiServer.class);
        server.start();
    }
}
