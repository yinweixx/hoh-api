package com.anyun.common.service.common;

import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.service.common.Service;
import com.anyun.common.lang.HashIdGenerator;
import com.anyun.common.lang.msg.NatsClient;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.anyun.cloud.service.common.CloudService;
import com.google.inject.Inject;
import io.nats.client.Connection;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class DefaultServiceDeployer implements ServiceDeployer {
    private static final String PATH_SERVICE = "/anyuncloud/service";
    private static final String QUEUE_PREFIX = "QUEUE-";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceDeployer.class);
    private ZookeeperClient zk;
    private NatsClient natsClient;
    private ServiceCache serviceCache;

    @Inject
    public DefaultServiceDeployer(ZookeeperClient zk, NatsClient natsClient, ServiceCache serviceCache) {
        this.zk = zk;
        this.natsClient = natsClient;
        this.serviceCache = serviceCache;
    }

    @Override
    public void deploy(String deviceId, Class<? extends Service> cloudServiceClass) throws Exception {
        CloudService cloudService = cloudServiceClass.getAnnotation(CloudService.class);
        String pathInfo = cloudService.servicePath();
        LOGGER.debug("Deploying service path: {}", pathInfo);
        String resourceId = HashIdGenerator.generate(pathInfo);
        LOGGER.debug("DeviceId [{}] ServiceId [{}]", deviceId, resourceId);
        String servicePath = PATH_SERVICE + "/" + resourceId;
        LOGGER.debug("Deploying service path: {}", servicePath);
        String devicePath = servicePath + "/" + deviceId;
        ServiceDeployEntity entity = new ServiceDeployEntity();
        entity.setDateTime(new Date());
        entity.setDevice(deviceId);
        String data = GsonUtil.getUtil().toJson(entity);
        zk.createPath(devicePath, data, CreateMode.EPHEMERAL);
        LOGGER.debug("Deploy resource [{}] to zookeeper,data [\n{}\n]", resourceId, data);
        Connection connection = natsClient.getConnection();
        String queueName = QUEUE_PREFIX + resourceId;
        serviceCache.addService(resourceId, cloudServiceClass.newInstance());
        connection.subscribe(resourceId, queueName, new ServiceMessageHandler(resourceId, connection));
        LOGGER.debug("Subscribe [{}][{}]", resourceId, queueName);
    }

    public static class ServiceDeployEntity {
        private Date dateTime;
        private String device;

        public Date getDateTime() {
            return dateTime;
        }

        public void setDateTime(Date dateTime) {
            this.dateTime = dateTime;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }
    }
}
