package com.anyun.cloud.demo.monitor.modules;

import com.anyun.cloud.demo.monitor.message.DefaultNatsClient;
import com.anyun.cloud.demo.monitor.message.DefaultNatsStatisticsService;
import com.anyun.cloud.demo.monitor.message.NatsClient;
import com.anyun.cloud.demo.monitor.message.NatsStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public class NatsModule extends AbstractCloudModules {
    private static final Logger LOGGER = LoggerFactory.getLogger(NatsModule.class);

    @Override
    protected void init() throws Exception {
        bind(NatsClient.class).to(DefaultNatsClient.class);
        bind(NatsStatisticsService.class).to(DefaultNatsStatisticsService.class);
        LOGGER.info("Bind NatsStatisticsService to: {}", DefaultNatsStatisticsService.class.getCanonicalName());
    }
}
