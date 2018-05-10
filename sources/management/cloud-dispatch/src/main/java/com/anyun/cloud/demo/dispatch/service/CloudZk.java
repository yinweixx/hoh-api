package com.anyun.cloud.demo.dispatch.service;

import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class CloudZk implements Zk {
    private static final String ZK_PATH_API = "/anyuncloud/service";
    private ZookeeperClient zk;

    @Inject
    public CloudZk(ZookeeperClient zk) {
        this.zk = zk;
    }

    @Override
    public List<String> getDeployedApiServiceNames() throws Exception {
        List<String> deployedApiNodeNames = new ArrayList<>();
        List<String> apiNodeNames = zk.getChildren(ZK_PATH_API);
        for (String name : apiNodeNames) {
            String path = ZK_PATH_API + "/" + name;
            if (!zk.exist(path))
                continue;
            List<String> apiDeployedNodeNames = zk.getChildren(path);
            if (!apiDeployedNodeNames.isEmpty())
                deployedApiNodeNames.add(name);
        }
        return deployedApiNodeNames;
    }
}
