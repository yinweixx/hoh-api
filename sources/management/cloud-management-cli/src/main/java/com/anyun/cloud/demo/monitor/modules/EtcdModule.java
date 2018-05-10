package com.anyun.cloud.demo.monitor.modules;

import com.anyun.cloud.demo.common.etcd.client.ClientConfig;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.client.OkHttpRestfullyApiClient;
import com.google.inject.name.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public class EtcdModule extends AbstractCloudModules {
    private static final Logger LOGGER = LoggerFactory.getLogger(EtcdModule.class);

    @Override
    protected void init() throws Exception {
        ClientConfig etcdApiClientConfig = new ClientConfig();
        etcdApiClientConfig.setHost("node1.etcd.dev.hohhot.ga.gov");
        HttpRestfullyApiClient etcdClient = new OkHttpRestfullyApiClient(etcdApiClientConfig);
        bind(HttpRestfullyApiClient.class).annotatedWith(Names.named("etcd")).toInstance(etcdClient);
        bind(HttpRestfullyApiClient.class).toInstance(etcdClient);
        LOGGER.info("Bind etcd api client to: {}", OkHttpRestfullyApiClient.class.getCanonicalName());
    }
}
