package com.anyun.common.service.common;

import com.anyun.cloud.demo.common.etcd.client.ClientConfig;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.client.OkHttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.spi.EtcdExtendService;
import com.anyun.cloud.demo.common.etcd.spi.impl.EtcdExtenedServiceImpl;
import com.anyun.cloud.demo.common.registry.client.DefaultRegisterClient;
import com.anyun.cloud.demo.common.registry.client.RegisterClient;
import com.anyun.cloud.demo.common.registry.service.NodeRegistService;
import com.anyun.cloud.demo.common.registry.service.impl.NodeRegistServiceImpl;
import com.anyun.cloud.demo.common.registry.utils.DefaultZookeeperClient;
import com.anyun.common.lang.options.ApplicationOptions;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class ServiceModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceModule.class);
    private ServiceOptions options;

    public ServiceModule(ServiceOptions options) {
        this.options = options;
    }

    @Override
    protected void configure() {
        bind(ApplicationOptions.class).toInstance(options);
        ClientConfig etcdApiClientConfig = new ClientConfig();
        etcdApiClientConfig.setHost(getEtcdAddress());
        HttpRestfullyApiClient etcdClient = new OkHttpRestfullyApiClient(etcdApiClientConfig);
        EtcdExtendService etcdExtenedService = new EtcdExtenedServiceImpl(etcdClient);
        bind(EtcdExtendService.class).toInstance(etcdExtenedService);
        bind(ZookeeperClient.class).to(DefaultZookeeperClient.class);
        bind(NodeRegistService.class).to(NodeRegistServiceImpl.class);
        bind(RegisterClient.class).to(DefaultRegisterClient.class);
    }

    private String getEtcdAddress() {
        if (!options.getCommandLine().hasOption("etcdaddr")) {
            LOGGER.error("Not find etc address argument");
            System.exit(1);
        }
        return options.getCommandLine().getOptionValue("etcdaddr");
    }
}
