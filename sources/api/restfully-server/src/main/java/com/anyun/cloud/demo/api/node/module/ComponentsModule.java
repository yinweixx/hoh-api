package com.anyun.cloud.demo.api.node.module;

import com.anyun.cloud.demo.api.node.core.DefaultApiCache;
import com.anyun.cloud.demo.api.node.core.DefaultApiDeployer;
import com.anyun.cloud.demo.api.node.core.DefaultApiFinder;
import com.anyun.cloud.demo.api.node.core.DefaultNodeApiComponent;
import com.anyun.cloud.demo.api.node.core.common.ApiCache;
import com.anyun.cloud.demo.api.node.core.common.ApiDeployer;
import com.anyun.cloud.demo.api.node.core.common.ApiFinder;
import com.anyun.cloud.demo.api.node.core.common.NodeApiComponent;
import com.google.inject.AbstractModule;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public class ComponentsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ApiCache.class).to(DefaultApiCache.class);
        bind(ApiFinder.class).to(DefaultApiFinder.class);
        bind(ApiDeployer.class).to(DefaultApiDeployer.class);

        bind(NodeApiComponent.class).to(DefaultNodeApiComponent.class);
    }
}
