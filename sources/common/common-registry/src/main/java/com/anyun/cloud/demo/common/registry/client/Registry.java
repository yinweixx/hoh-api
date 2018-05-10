package com.anyun.cloud.demo.common.registry.client;

import com.anyun.common.lang.bean.InjectorsBuilder;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 07/06/2017
 */
public class Registry {
    public static void main(String[] args) throws Exception {
        RegistryOptions options = new RegistryOptions(args);
        InjectorsBuilder injectorsBuilder = InjectorsBuilder.getBuilder();
        injectorsBuilder.build(new RegistryModule(options));
        RegisterClient registerClient = injectorsBuilder.getInstanceByType(RegisterClient.class);
        registerClient.regist();
        registerClient.loopThread();
    }
}
