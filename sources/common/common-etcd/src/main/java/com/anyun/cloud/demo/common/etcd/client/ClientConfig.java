package com.anyun.cloud.demo.common.etcd.client;

import javax.inject.Singleton;
import java.io.Serializable;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
@Singleton
public class ClientConfig implements Serializable {
    private String schema = "http";
    private String host = "etcd.dev.hohhot.ga.gov";
    private int port = 2379;
    private String basePathSegment = "v2";

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBasePathSegment() {
        return basePathSegment;
    }

    public void setBasePathSegment(String basePathSegment) {
        this.basePathSegment = basePathSegment;
    }

    @Override
    public String toString() {
        return "ClientConfig{" +
                "schema='" + schema + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", basePathSegment='" + basePathSegment + '\'' +
                '}';
    }
}
