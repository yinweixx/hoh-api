package com.anyun.cloud.demo.dispatch;

import com.anyun.cloud.demo.dispatch.service.*;
import com.google.inject.AbstractModule;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class DispatchModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Dispatcher.class).to(DefaultDispatcher.class);
        bind(ApiService.class).to(DefaultApiService.class);
        bind(Etcd.class).to(CloudEtcd.class);
        bind(Zk.class).to(CloudZk.class);
    }
}
