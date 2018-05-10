package com.anyun.cloud.demo.api.management.http.response.entity;

import com.anyun.common.lang.http.entity.BaseResponseEntity;
import com.google.gson.annotations.SerializedName;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
public class VersionResponseEntity implements BaseResponseEntity {
    @SerializedName("etcd.server")
    private String etcdServer = "3.2.0-rc.0";
    @SerializedName("etcd.cluster")
    private String etcdcluster = "3.2.0";
    @SerializedName("zookeeper")
    private String zookeeper = "3.4.10";
    @SerializedName("jetty")
    private String jetty = "9.4.0.RC3";
    @SerializedName("api")
    private String api = "v1.0.0";

    public String getEtcdServer() {
        return etcdServer;
    }

    public void setEtcdServer(String etcdServer) {
        this.etcdServer = etcdServer;
    }

    public String getEtcdcluster() {
        return etcdcluster;
    }

    public void setEtcdcluster(String etcdcluster) {
        this.etcdcluster = etcdcluster;
    }

    public String getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(String zookeeper) {
        this.zookeeper = zookeeper;
    }

    public String getJetty() {
        return jetty;
    }

    public void setJetty(String jetty) {
        this.jetty = jetty;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
