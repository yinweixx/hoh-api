package com.anyun.cloud.demo.message.client;

import io.nats.client.Message;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/06/2017
 */
public interface NatsClient {

    /**
     *
     * @param channel
     * @param data
     * @return
     * @throws Exception
     */
    Message request(String channel, String data) throws Exception;
}
