package com.anyun.cloud.demo.message.client.connection;

import io.nats.client.ClosedCallback;
import io.nats.client.ConnectionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/06/2017
 */
public class DefaultClosedCallback implements ClosedCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClosedCallback.class);

    @Override
    public void onClose(ConnectionEvent connectionEvent) {
        LOGGER.warn("Connection closed of [{}]",connectionEvent.getConnection().getConnectedServerId());
    }
}
