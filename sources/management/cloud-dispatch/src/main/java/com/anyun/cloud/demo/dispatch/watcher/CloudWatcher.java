package com.anyun.cloud.demo.dispatch.watcher;

import com.anyun.common.lang.zookeeper.ZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public abstract class CloudWatcher implements CuratorWatcher {
    private String watchPath = "";
    private ZookeeperClient zk;
    private CuratorFramework curatorFramework;

    public CloudWatcher(ZookeeperClient zk) {
        this.zk = zk;
        this.curatorFramework = zk.getContext(CuratorFramework.class.getName(), CuratorFramework.class);
    }

    public CloudWatcher withPath(String path) {
        this.watchPath = path;
        return this;
    }

    public void start() throws Exception {
        curatorFramework.getChildren().usingWatcher(this).forPath(watchPath);
    }

    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {
        watch(watchedEvent);
    }

    private void watch(WatchedEvent watchedEvent) throws Exception {
        curatorFramework.getChildren().usingWatcher(this).forPath(watchPath);
        watcherProcess(watchedEvent);
    }

    public abstract void watcherProcess(WatchedEvent watchedEvent) throws Exception;

    protected ZookeeperClient getZk() {
        return zk;
    }

    public String getWatchPath() {
        return watchPath;
    }
}
