package com.anyun.cloud.demo.common.registry.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class NodeNetworkEntity {
    @SerializedName("name")
    private String etherName;
    @SerializedName("network_ip")
    private List<String> ip;
    @SerializedName("network_mac")
    private String mac;

    public List<String> getIp() {
        return ip;
    }

    public void setIp(List<String> ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEtherName() {
        return etherName;
    }

    public void setEtherName(String etherName) {
        this.etherName = etherName;
    }
}
