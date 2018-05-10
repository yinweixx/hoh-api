package com.anyun.cloud.demo.monitor.modules;

import com.google.inject.AbstractModule;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public abstract class AbstractCloudModules extends AbstractModule {

    @Override
    protected final void configure() {
        try {
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    protected abstract void init() throws Exception;
}
