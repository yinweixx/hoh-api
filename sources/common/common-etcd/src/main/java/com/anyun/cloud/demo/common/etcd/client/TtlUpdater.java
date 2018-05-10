package com.anyun.cloud.demo.common.etcd.client;

import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.common.lang.thread.ScheduledExecutorServiceRunner;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/06/2017
 */
public class TtlUpdater extends ScheduledExecutorServiceRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtlUpdater.class);
    private static final String NAME_PREFIX = "ttl-updater-";
    private static final String URI_BASE = "/keys/discover/";
    private HttpRestfullyApiClient client;
    private String nodeId;

    @Inject
    public TtlUpdater(HttpRestfullyApiClient client) {
        this.client = client;
        withLoopTime(3);
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
        setName(NAME_PREFIX + nodeId);
    }

    @Override
    protected Runnable buildRunnable() {
        return () -> {
            String pathSegments = URI_BASE + nodeId;
            String sb = "ttl=5&refresh=true&prevExist=";
            try {
                String etcdResponseJson = client.put(pathSegments, sb + "true", null);
                GsonUtil.getUtil().getReponseEntity(etcdResponseJson);
                LOGGER.debug("Node TTL [{}] update success", nodeId);
            } catch (Exception e) {
                try {
                    client.put(pathSegments, sb + "false", null);
                    LOGGER.debug("Node TTL [{}] update successful", nodeId);
                } catch (Exception e1) {
                    LOGGER.error("Node TTL [{}] update unsuccessful", nodeId);
                }
            }
        };
    }
}
