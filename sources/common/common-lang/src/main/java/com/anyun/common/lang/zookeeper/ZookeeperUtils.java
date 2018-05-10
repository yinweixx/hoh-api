package com.anyun.common.lang.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class ZookeeperUtils {
    public static void main(String[] args) throws Exception{
        String zookeeperConnectionString = "192.168.103.4:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        client.start();
        String path = "/anyuncloud/nodes";
        if(client.checkExists().forPath(path) == null)
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
    }
}
