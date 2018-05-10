package com.anyun.common.lang.msg;

import io.nats.client.Connection;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public interface NatsClient {

    default void restart() throws Exception {
        stop();
        start();
    }

    /**
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * @throws Exception
     */
    void stop() throws Exception;

    /**
     *
     * @return
     */
    Connection getConnection();
}
