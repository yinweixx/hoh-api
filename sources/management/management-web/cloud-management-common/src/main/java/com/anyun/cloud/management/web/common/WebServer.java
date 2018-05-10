package com.anyun.cloud.management.web.common;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public interface WebServer {

    /**
     * start web server
     *
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * stop web server
     *
     * @throws Exception
     */
    void stop() throws Exception;

    /**
     * restart web server
     *
     * @throws Exception
     */
    default void restart() throws Exception {
        //default server restart implement
        stop();
        start();
    }
}
