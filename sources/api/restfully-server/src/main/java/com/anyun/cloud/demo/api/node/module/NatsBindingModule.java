package com.anyun.cloud.demo.api.node.module;

import com.anyun.cloud.demo.common.etcd.client.DefaultNatsClient;
import com.anyun.common.lang.msg.NatsClient;
import com.google.inject.AbstractModule;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public class NatsBindingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(NatsClient.class).to(DefaultNatsClient.class);
    }
}
