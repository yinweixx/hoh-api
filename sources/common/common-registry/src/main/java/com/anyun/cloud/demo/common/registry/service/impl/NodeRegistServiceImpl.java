package com.anyun.cloud.demo.common.registry.service.impl;

import com.anyun.cloud.demo.common.registry.entity.NodeEntity;
import com.anyun.cloud.demo.common.registry.entity.NodeNetworkEntity;
import com.anyun.cloud.demo.common.registry.entity.NodeType;
import com.anyun.cloud.demo.common.registry.service.NodeRegistService;
import com.anyun.cloud.demo.common.registry.utils.DeviceIdGenerator;
import com.anyun.common.lang.json.GsonUtil;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class NodeRegistServiceImpl implements NodeRegistService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeRegistService.class);
    private ZookeeperClient zkClient;

    @Inject
    public NodeRegistServiceImpl(ZookeeperClient zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public String registNode(NodeEntity nodeEntity) throws Exception {
        if (nodeEntity == null)
            throw new Exception("Node info is null");
        if (!zkClient.exist(PATH_NODE)) {
            LOGGER.info("Node [{}] not exist,create it", PATH_NODE);
            zkClient.createPath(PATH_NODE, CreateMode.PERSISTENT);
        }
        String path = PATH_NODE + "/" + nodeEntity.getUid();
        zkClient.createPath(path, GsonUtil.getUtil().toJson(nodeEntity), CreateMode.EPHEMERAL);
        return nodeEntity.getUid();
    }

    @Override
    public String registNode(NodeType... nodeTypes) throws Exception {
        NodeEntity entity = new NodeEntity();
        entity.setNodeType(Arrays.asList(nodeTypes));
        entity.setTimestamp(System.currentTimeMillis());
        entity.setUid(DeviceIdGenerator.getGenerator().generate());
        entity.setUpstartTimestamp(System.currentTimeMillis());
        List<NodeNetworkEntity> nodeNetworkEntities = DeviceIdGenerator.getDeviceNetworks();
        entity.setNetworks(nodeNetworkEntities);
        return registNode(entity);
    }
}
