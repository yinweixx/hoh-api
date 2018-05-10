package com.anyun.cloud.demo.monitor.message;

import com.anyun.cloud.demo.monitor.message.entity.NatsRuntimeStatisticsEntity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public interface NatsStatisticsService {
    NatsRuntimeStatisticsEntity getStatistics() throws Exception;
}
