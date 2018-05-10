package com.anyun.cloud.demo.message.client.statistics;

import com.google.inject.Inject;
import io.nats.client.Connection;
import io.nats.client.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/06/2017
 */
public class StatisticsRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsRunnable.class);
    private static final int INITIAL_DELAY = 0;
    private Connection connection;
    private ScheduledExecutorService executor;
    private int loopTime = 1;
    private Exception lastException;
    private long lastStopTime;

    @Inject
    public StatisticsRunnable(Connection connection) {
        this.connection = connection;
    }

    public StatisticsRunnable withLoopTime(int loopTime) {
        if (loopTime > this.loopTime)
            this.loopTime = loopTime;
        return this;
    }

    public void start() throws Exception {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this, INITIAL_DELAY, loopTime, TimeUnit.SECONDS);
    }

    public void stop() throws Exception {
        executor.shutdown();
        lastStopTime = System.currentTimeMillis();
        LOGGER.debug("Stopping Nats server [{}] statistics", connection.getConnectedServerId());
    }

    public Exception getLastException() {
        return lastException;
    }

    public long getLastStopTime() {
        return lastStopTime;
    }

    @Override
    public void run() {
        LOGGER.debug("Starting Nats server [{}] statistics", connection.getConnectedServerId());
        try {
            Statistics statistics = connection.getStats();
            LOGGER.debug("statistics info: {}", statistics);
            connection.getConnectedServerInfo();
            Thread.sleep(loopTime * 1000);
        } catch (Exception ex) {
            this.lastException = ex;
            lastStopTime = System.currentTimeMillis();
            LOGGER.error("Nats server [{}] statistics error: {}", connection.getConnectedServerId(), ex.getMessage(), ex);
            executor.shutdown();
        }
    }
}
