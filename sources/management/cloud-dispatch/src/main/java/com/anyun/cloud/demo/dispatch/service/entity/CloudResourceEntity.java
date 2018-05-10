package com.anyun.cloud.demo.dispatch.service.entity;

import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class CloudResourceEntity {
    private String resourceId;
    private ApiResourceEntity entity;

    public CloudResourceEntity() {
    }

    public CloudResourceEntity(String resourceId, ApiResourceEntity entity) {
        this.resourceId = resourceId;
        this.entity = entity;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public ApiResourceEntity getEntity() {
        return entity;
    }

    public void setEntity(ApiResourceEntity entity) {
        this.entity = entity;
    }
}
