package com.anyun.cloud.demo.dispatch.watcher;

import com.anyun.common.lang.zookeeper.ZookeeperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class NodesWatcher extends CloudNodesWatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodesWatcher.class);

    public NodesWatcher(ZookeeperClient zk) throws Exception {
        super(zk);
    }

    @Override
    protected void increment(List<String> nodePaths) {
        for (String node : nodePaths) {
            LOGGER.debug("Increment node: {}", node);
        }
    }

    @Override
    protected void decrement(List<String> nodePaths) {
        for (String node : nodePaths) {
            LOGGER.debug("Decrement node: {}", node);
        }
    }
}
