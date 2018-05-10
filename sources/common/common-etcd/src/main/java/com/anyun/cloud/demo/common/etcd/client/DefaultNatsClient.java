package com.anyun.cloud.demo.common.etcd.client;

import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionNode;
import com.anyun.cloud.demo.common.etcd.spi.EtcdExtendService;
import com.anyun.common.lang.msg.NatsClient;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
@Singleton
public class DefaultNatsClient implements NatsClient, Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNatsClient.class);
    private static final String PATH_NATS_CONF = "/keys/config/nats";
    private HttpRestfullyApiClient etcd;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Thread natsThread;
    private CountDownLatch latch;
    @Inject
    public DefaultNatsClient(EtcdExtendService etcdExtendService) {
        this.etcd = etcdExtendService.getClient();
    }

    @Override
    public void start() throws Exception {
        natsThread = new Thread(this);
        latch = new CountDownLatch(1);
        natsThread.start();
        latch.await();
    }

    @Override
    public void stop() throws Exception {
        connection.close();
        connection = null;
        connectionFactory = null;
        natsThread = null;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void run() {
        try {
            String configJson = etcd.get(PATH_NATS_CONF, null);
            LOGGER.debug("Nats connection config: {}", configJson);
            EtcdActionNode response = GsonUtil.getUtil().getReponseEntity(configJson).getActionNode();
            String connectionString = response.getNodeValueByName("connection-string");
            LOGGER.debug("Nats connection string: {}", connectionString);
            connectionFactory = new ConnectionFactory(connectionString);
            connection = connectionFactory.createConnection();
            LOGGER.info("Nats server connection created");
            latch.countDown();
        } catch (Exception ex) {
            LOGGER.error("Nats client start error: {}", ex.getMessage(), ex);
            System.exit(1);
        }
    }
}
