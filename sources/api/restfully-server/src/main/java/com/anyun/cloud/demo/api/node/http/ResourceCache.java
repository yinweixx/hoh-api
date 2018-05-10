package com.anyun.cloud.demo.api.node.http;

import com.google.inject.Singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
@Singleton
public class ResourceCache {
    private AtomicInteger countDownLatch = new AtomicInteger(0);

    public void resourceIncrement() {
        countDownLatch.getAndIncrement();
    }

    public void resourceDecrement() {
        countDownLatch.getAndDecrement();
    }

    public boolean isAllResourceFinish() {
        return countDownLatch.get() == 0;
    }

    public int getCurrentResourcesCount() {
        return countDownLatch.get();
    }
}
