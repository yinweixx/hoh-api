package com.anyun.cloud.demo.message.client;

import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.Message;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public class DefaultNatsClient implements NatsClient {
    private ConnectionFactory factory;
    private Connection connection;


    public DefaultNatsClient() {
    }

    @Override
    public Message request(String channel, String data) throws Exception {
        return null;
    }
}
