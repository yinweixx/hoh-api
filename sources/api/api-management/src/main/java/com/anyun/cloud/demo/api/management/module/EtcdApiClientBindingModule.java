package com.anyun.cloud.demo.api.management.module;

import com.anyun.cloud.demo.common.etcd.client.ClientConfig;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.client.OkHttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.client.TtlUpdater;
import com.anyun.cloud.demo.common.etcd.spi.EtcdExtendService;
import com.anyun.cloud.demo.common.etcd.spi.impl.EtcdExtenedServiceImpl;
import com.anyun.common.lang.thread.ScheduledExecutorServiceRunner;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class EtcdApiClientBindingModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(EtcdApiClientBindingModule.class);

    @Override
    protected void configure() {
        ClientConfig etcdApiClientConfig = new ClientConfig();
        etcdApiClientConfig.setHost("node1.etcd.dev.hohhot.ga.gov");
        bind(ClientConfig.class).toInstance(etcdApiClientConfig);
        LOGGER.info("Bind etcd api client config to: {}", etcdApiClientConfig.toString());

        bind(HttpRestfullyApiClient.class).to(OkHttpRestfullyApiClient.class);
        LOGGER.info("Bind etcd api client to: {}", OkHttpRestfullyApiClient.class.getCanonicalName());

        bind(EtcdExtendService.class).to(EtcdExtenedServiceImpl.class);
        LOGGER.info("Bind etcd api extend service to: {}", EtcdExtenedServiceImpl.class.getCanonicalName());

        bind(ScheduledExecutorServiceRunner.class).annotatedWith(Names.named("TTL-UPDATER")).to(TtlUpdater.class);
        LOGGER.info("Bind TTL updater to: {}", TtlUpdater.class.getCanonicalName());
    }
}
