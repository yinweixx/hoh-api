package test.com.anyun.cloud.demo.dispatch;

import com.anyun.cloud.demo.common.etcd.spi.entity.ZookeeperConfigEntity;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        ZookeeperConfigEntity config = new ZookeeperConfigEntity();
        String zookeeperConnectionString = "zookeeper.dev.hohhot.ga.gov:2181";
        int sleepTime = config.getRetryPolicySleepTime();
        int maxRetries = config.getRetryPolicyMaxRetries();
        int sessionTimeout = config.getSessionTimeout();
        int connTimeout = config.getConnectionTimeout();
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(sleepTime, maxRetries);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
                zookeeperConnectionString, sessionTimeout, connTimeout, retryPolicy);
        curatorFramework.start();
        new TestCuratorWatcher(curatorFramework, "/anyuncloud/nodes").start();
        Thread.currentThread().join();
    }

    public static class TestCuratorWatcher implements CuratorWatcher {
        private CuratorFramework curatorFramework;
        private String path;

        public TestCuratorWatcher(CuratorFramework curatorFramework, String path) {
            this.curatorFramework = curatorFramework;
            this.path = path;
        }

        public void start() throws Exception {
            curatorFramework.getChildren().usingWatcher(this).forPath(path);
        }

        @Override
        public void process(WatchedEvent watchedEvent) throws Exception {
            System.out.println(watchedEvent);
            curatorFramework.getChildren().usingWatcher(this).forPath(path);
        }
    }
}
