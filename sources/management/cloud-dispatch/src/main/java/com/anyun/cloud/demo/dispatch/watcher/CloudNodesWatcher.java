package com.anyun.cloud.demo.dispatch.watcher;

import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;
import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public abstract class CloudNodesWatcher extends CloudWatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudNodesWatcher.class);
    private List<String> lastNodeNames = new ArrayList<>();

    @Inject
    public CloudNodesWatcher(ZookeeperClient zk) throws Exception {
        super(zk);
    }

    @Override
    public void start() throws Exception {
        scanNodes();
        super.start();
    }

    @Override
    public void watcherProcess(WatchedEvent watchedEvent) throws Exception {
        List<String> nodeNames = getZk().getChildren(getWatchPath());
        LOGGER.debug("Active node size: {}, Last scan active node size: {}", nodeNames.size(), lastNodeNames.size());
        if (nodeNames.size() > lastNodeNames.size())
            increment(getIncrementNames(nodeNames, lastNodeNames));
        else
            decrement(getIncrementNames(lastNodeNames, nodeNames));
        scanNodes();
    }

    private List<String> getIncrementNames(List<String> names1, List<String> names2) {
        List<String> incrementNames = new ArrayList<>();
        for (String name : names1) {
            boolean flag = false;
            for (String lname : names2) {
                if (lname.equals(name)) {
                    flag = true;
                    break;
                }
            }
            if (flag == false)
                incrementNames.add(this.getWatchPath() + "/" + name);
        }
        return incrementNames;
    }

    private void scanNodes() throws Exception {
        lastNodeNames = getZk().getChildren(getWatchPath());
        LOGGER.debug("Active node size: {}", lastNodeNames.size());
    }

    protected abstract void increment(List<String> nodePaths);

    protected abstract void decrement(List<String> nodePaths);
}
