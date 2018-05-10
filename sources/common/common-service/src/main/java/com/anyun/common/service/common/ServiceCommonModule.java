package com.anyun.common.service.common;

import com.anyun.cloud.demo.common.etcd.client.DefaultNatsClient;
import com.anyun.common.lang.msg.NatsClient;
import com.anyun.common.service.git.DefaultGitService;
import com.anyun.common.service.git.GitService;
import com.google.inject.AbstractModule;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class ServiceCommonModule extends AbstractModule {
    private CommServiceOptions commServiceOptions;

    public ServiceCommonModule(CommServiceOptions commServiceOptions) {
        this.commServiceOptions = commServiceOptions;
    }

    @Override
    protected void configure() {
        bind(ServiceCache.class).toInstance(new ServiceCache());
        bind(NatsClient.class).to(DefaultNatsClient.class);
        bind(ServiceDeployer.class).to(DefaultServiceDeployer.class);
        bind(CommServiceOptions.class).toInstance(commServiceOptions);
        bind(GitService.class).to(DefaultGitService.class);
    }
}
