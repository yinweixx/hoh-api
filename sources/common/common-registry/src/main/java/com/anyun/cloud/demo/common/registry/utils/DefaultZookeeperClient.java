package com.anyun.cloud.demo.common.registry.utils;

import com.anyun.cloud.demo.common.etcd.spi.EtcdExtendService;
import com.anyun.cloud.demo.common.etcd.spi.entity.ZookeeperConfigEntity;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
@Singleton
public class DefaultZookeeperClient implements ZookeeperClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperClient.class);
    private CuratorFramework curatorFramework;

    @Inject
    public DefaultZookeeperClient(EtcdExtendService extenedService) throws Exception {
        ZookeeperConfigEntity config = extenedService.getZookeeperConfigResponse();
        String zookeeperConnectionString = config.getConnectingString();
        int sleepTime = config.getRetryPolicySleepTime();
        int maxRetries = config.getRetryPolicyMaxRetries();
        int sessionTimeout = config.getSessionTimeout();
        int connTimeout = config.getConnectionTimeout();
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(sleepTime, maxRetries);
        curatorFramework = CuratorFrameworkFactory.newClient(
                zookeeperConnectionString, sessionTimeout, connTimeout, retryPolicy);
    }


    @Override
    public boolean exist(String path) throws Exception {
        if (curatorFramework.checkExists().forPath(path) == null)
            return false;
        return true;
    }

    @Override
    public void createPath(String path, CreateMode createMode) throws Exception {
        if (exist(path)) {
            LOGGER.warn("Path [{}] exist..", path);
            return;
        }
        curatorFramework.create().creatingParentsIfNeeded().withMode(createMode).forPath(path);
    }

    @Override
    public void createPath(String path, String data, CreateMode createMode) throws Exception {
        if (exist(path)) {
            LOGGER.warn("Path [{}] exist..", path);
            return;
        }
        curatorFramework.create().creatingParentsIfNeeded().withMode(createMode).forPath(path, data.getBytes());
    }

    @Override
    public List<String> getChildren(String path) throws Exception {
        if (!exist(path)) {
            LOGGER.warn("Path [{}] exist..", path);
            return null;
        }
        return curatorFramework.getChildren().forPath(path);
    }

    @Override
    public String getStringData(String path) throws Exception {
        if (!exist(path)) {
            LOGGER.warn("Path [{}] exist..", path);
            return null;
        }
        byte[] data = curatorFramework.getData().forPath(path);
        if (data == null || data.length == 0)
            return null;
        return new String(data);
    }

    @Override
    public void start() throws Exception {
        curatorFramework.start();
        LOGGER.info("Zookeeper client [{}] started...", curatorFramework.hashCode());
    }

    @Override
    public void stop() throws Exception {
        curatorFramework.close();
        LOGGER.info("Zookeeper client [{}] closed...", curatorFramework.hashCode());
    }

    @Override
    public void restart() throws Exception {
        stop();
        start();
    }

    @Override
    public <T> T getContext(String query, Class<T> T) {
        if (query.equals(CuratorFramework.class.getName()))
            return (T) curatorFramework;
        return null;
    }
}
