package com.anyun.cloud.demo.api.node.core.common.entity;

import java.util.Date;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public class ApiZkDeployedEntity {
    private Date dateTime;
    private String device;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
