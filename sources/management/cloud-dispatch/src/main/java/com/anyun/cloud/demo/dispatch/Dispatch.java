package com.anyun.cloud.demo.dispatch;

import com.anyun.cloud.demo.common.registry.client.RegistryModule;
import com.anyun.cloud.demo.common.registry.client.RegistryOptions;
import com.anyun.common.lang.bean.InjectorsBuilder;

import java.util.concurrent.CountDownLatch;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public class Dispatch {
    public static void main(String[] args) throws Exception {
        RegistryOptions options = new RegistryOptions(args);
        InjectorsBuilder injectorsBuilder = InjectorsBuilder.getBuilder();
        injectorsBuilder.build(
                new RegistryModule(options),
                new DispatchModule());
        Dispatcher dispatcher = InjectorsBuilder.getBuilder().getInstanceByType(Dispatcher.class);
        dispatcher.start();
        new CountDownLatch(1).await();
    }
}
