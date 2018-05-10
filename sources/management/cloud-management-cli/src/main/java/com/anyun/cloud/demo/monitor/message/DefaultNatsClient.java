package com.anyun.cloud.demo.monitor.message;

import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.demo.common.etcd.client.ClientConfig;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.client.OkHttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.net.URI;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
@Singleton
public class DefaultNatsClient implements NatsClient {
    public static final String KEY_CONFIG_NATS = "/keys/config/nats";
    public static final String KEY_NATS_CONNECTION_STR = "connection-string";
    public static final String KEY_NATS_WEB_PORT = "http-port";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNatsClient.class);
    private ConnectionFactory connectionFactory;
    private HttpRestfullyApiClient apiClient;
    private HttpRestfullyApiClient mgrClient;
    private Connection connection;

    @Inject
    public DefaultNatsClient(@Named("etcd") HttpRestfullyApiClient apiClient) throws Exception {
        this.apiClient = apiClient;
        String json = apiClient.get(KEY_CONFIG_NATS, null);
        LOGGER.debug("Nats config: {}", json);
        EtcdActionResponse response = GsonUtil.getUtil().getReponseEntity(json);
        String connectionString = response.getActionNode().getNodeValueByName(KEY_NATS_CONNECTION_STR);
        LOGGER.debug("Nats connection string: {}", connectionString);
        connectionFactory = new ConnectionFactory(connectionString);
        connection = connectionFactory.createConnection();
        buildMgrClient(connectionString, response);
    }

    private void buildMgrClient(String connectionString, EtcdActionResponse response) throws Exception {
        String[] addr = connectionString.split(",");
        ClientConfig config = new ClientConfig();
        URI uri = URI.create(addr[0]);
        config.setHost(uri.getHost());
        config.setPort(Integer.valueOf(response.getActionNode().getNodeValueByName(KEY_NATS_WEB_PORT)));
        config.setSchema("http");
        config.setBasePathSegment("/");
        LOGGER.debug("Nats management http config: {}", config.toString());
        mgrClient = new OkHttpRestfullyApiClient(config);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public HttpRestfullyApiClient getMgrClient() {
        return mgrClient;
    }
}
