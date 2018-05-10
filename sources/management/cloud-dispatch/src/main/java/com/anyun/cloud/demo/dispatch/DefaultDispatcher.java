package com.anyun.cloud.demo.dispatch;

import com.anyun.cloud.demo.dispatch.service.ApiService;
import com.anyun.cloud.demo.dispatch.watcher.NodesWatcher;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class DefaultDispatcher implements Dispatcher {

    private ZookeeperClient zk;
    private ApiService apiService;

    @Inject
    public DefaultDispatcher(ApiService apiService, ZookeeperClient zk) {
        this.zk = zk;
        this.apiService = apiService;
    }

    @Override
    public void start() throws Exception {
        zk.start();
        List<String> names = apiService.getNotDeployedServiceIds();

        bootZkWatchers();
    }

    private void bootZkWatchers() throws Exception {
        new NodesWatcher(zk).withPath("/anyuncloud/nodes").start();
    }
}
