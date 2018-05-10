package com.anyun.cloud.demo.monitor.message;

import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import io.nats.client.Connection;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public interface NatsClient {
    Connection getConnection();
    HttpRestfullyApiClient getMgrClient();
}
