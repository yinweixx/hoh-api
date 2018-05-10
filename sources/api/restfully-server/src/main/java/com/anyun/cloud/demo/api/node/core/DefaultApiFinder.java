package com.anyun.cloud.demo.api.node.core;

import com.anyun.cloud.demo.api.node.core.common.ApiFinder;
import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionNode;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionResponse;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;
import com.anyun.common.lang.StringUtils;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public class DefaultApiFinder implements ApiFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApiFinder.class);
    private static final String PATH_API = "/anyuncloud/api";
    private static final String PATH_API_DEPLOY = "/keys/api/deploy";
    private ZookeeperClient zk;
    private HttpRestfullyApiClient apiClient;

    @Inject
    public DefaultApiFinder(HttpRestfullyApiClient apiClient, ZookeeperClient zk) {
        this.zk = zk;
        this.apiClient = apiClient;
    }

    @Override
    public List<String> findApiDeployNodesById(String id) throws Exception {
        String zkPath = PATH_API + "/" + id;
        List<String> nodeNames = new ArrayList<>();
        if (!zk.exist(zkPath)) {
            LOGGER.debug("API [{}] not deploy", id);
            return nodeNames;
        }
        List<String> deplpyNodeNames = zk.getChildren(zkPath);
        if (deplpyNodeNames == null || deplpyNodeNames.isEmpty()) {
            LOGGER.debug("API [{}] not deploy", id);
            return nodeNames;
        }
        LOGGER.debug("API [{}] deployed nodes: {}", id, deplpyNodeNames.toString());
        nodeNames.addAll(deplpyNodeNames);
        return nodeNames;
    }

    @Override
    public ApiResourceEntity findApiResourceDeployInfo(String id) throws Exception {
        EtcdActionResponse response = GsonUtil.getUtil()
                .getReponseEntity(apiClient.get(PATH_API_DEPLOY, null));
        List<EtcdActionNode> nodes = response.getActionNode().getActionNodes();
        if (nodes == null || nodes.isEmpty()) {
            LOGGER.debug("Not found deployed app space");
            return null;
        }
        for (EtcdActionNode node : nodes) {
            String spaceId = node.getKey().substring(node.getKey().lastIndexOf("/") + 1, node.getKey().length());
            LOGGER.debug("Found space id [{}]", spaceId);
            ApiResourceEntity entity = queryApiEntity(spaceId, id);
            if (entity != null)
                return entity;
        }
        return null;
    }

    private ApiResourceEntity queryApiEntity(String spaceId, String resourceId) {
        try {
            String path = PATH_API_DEPLOY + "/" + spaceId + "/resources/" + resourceId;

            EtcdActionResponse response = GsonUtil.getUtil()
                    .getReponseEntity(apiClient.get(path, null));
            if (response == null)
                return null;
            ApiResourceEntity entity = GsonUtil.getUtil().getGson()
                    .fromJson(response.getActionNode().getValue(), ApiResourceEntity.class);
            if (entity != null && StringUtils.isNotEmpty(entity.getPath())) {
                LOGGER.debug("Queried API resource structure： [\n{}\n]", entity.toString());
                return entity;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
}
