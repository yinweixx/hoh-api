package com.anyun.cloud.demo.api.node.core;

import com.anyun.cloud.demo.api.node.core.common.ApiDeployer;
import com.anyun.cloud.demo.api.node.core.common.entity.ApiDeployEntity;
import com.anyun.cloud.demo.api.node.core.common.entity.ApiZkDeployedEntity;
import com.anyun.cloud.demo.api.node.http.JettyApiNodeServer;
import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.common.lang.http.ApiServer;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public class DefaultApiDeployer implements ApiDeployer {
    private static final String PATH_API = "/anyuncloud/api";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApiFinder.class);
    private ZookeeperClient zk;
    private HttpRestfullyApiClient http;
    private JettyApiNodeServer apiServer;

    @Inject
    public DefaultApiDeployer(ZookeeperClient zk, HttpRestfullyApiClient http, ApiServer apiServer) {
        this.zk = zk;
        this.http = http;
        this.apiServer = (JettyApiNodeServer) apiServer;
    }

    @Override
    public void deploy(ApiDeployEntity deployEntity) throws Exception {
        String deviceId = apiServer.getDeviceId();
        String zkPath = PATH_API + "/" + deployEntity.getResourceId() + "/" + deviceId;
        ApiZkDeployedEntity entity = new ApiZkDeployedEntity();
        entity.setDateTime(new Date());
        entity.setDevice(deviceId);
        String data = GsonUtil.getUtil().toJson(entity);
        LOGGER.debug("Deploy resource [{}] to zookeeper,data [\n{}\n]", deployEntity.getResourceId(), entity);
        zk.createPath(zkPath, data, CreateMode.EPHEMERAL);
        LOGGER.debug("Add resource to local api node cache: ", deployEntity.getResourceId());
    }
}
