package com.anyun.cloud.demo.message.client.connection;

import io.nats.client.ClosedCallback;
import io.nats.client.ConnectionFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/06/2017
 */
public class NatsConnectionFactory {
    private ConnectionFactory connectionFactory;
    private ClosedCallback closedCallback;

    private NatsConnectionFactory withClosedCallback(ClosedCallback closedCallback) {
        this.closedCallback = closedCallback;
        return this;
    }

    public ConnectionFactory build() {
        connectionFactory = new ConnectionFactory();
        return connectionFactory;
    }
}
