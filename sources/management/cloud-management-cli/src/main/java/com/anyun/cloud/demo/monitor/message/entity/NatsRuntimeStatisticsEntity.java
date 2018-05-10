package com.anyun.cloud.demo.monitor.message.entity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public class NatsRuntimeStatisticsEntity {
    private NatsVarzEntity varz;
    private NatsConnzEntity connz;
    private NatsSubzEntity subz;

    public NatsVarzEntity getVarz() {
        return varz;
    }

    public void setVarz(NatsVarzEntity varz) {
        this.varz = varz;
    }

    public NatsConnzEntity getConnz() {
        return connz;
    }

    public void setConnz(NatsConnzEntity connz) {
        this.connz = connz;
    }

    public NatsSubzEntity getSubz() {
        return subz;
    }

    public void setSubz(NatsSubzEntity subz) {
        this.subz = subz;
    }
}
