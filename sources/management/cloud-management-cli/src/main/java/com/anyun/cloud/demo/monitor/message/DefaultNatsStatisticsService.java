package com.anyun.cloud.demo.monitor.message;

import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.cloud.demo.monitor.message.entity.NatsConnzEntity;
import com.anyun.cloud.demo.monitor.message.entity.NatsRuntimeStatisticsEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public class DefaultNatsStatisticsService implements NatsStatisticsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNatsStatisticsService.class);
    private NatsClient natsClient;
    private HttpRestfullyApiClient mgrClient;

    @Inject
    public DefaultNatsStatisticsService(NatsClient natsClient) {
        this.natsClient = natsClient;
        this.mgrClient = natsClient.getMgrClient();
    }

    @Override
    public NatsRuntimeStatisticsEntity getStatistics() throws Exception {
        String body = mgrClient.get("/connz", null);
        LOGGER.debug("/connz body: {}", body);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        NatsConnzEntity connz = gson.fromJson(body, NatsConnzEntity.class);
        System.out.println(connz);
        return null;
    }
}
