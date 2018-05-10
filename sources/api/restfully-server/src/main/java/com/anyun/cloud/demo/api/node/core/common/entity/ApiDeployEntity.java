package com.anyun.cloud.demo.api.node.core.common.entity;

import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public class ApiDeployEntity {
    private String resourceId;
    private ApiResourceEntity resource;
    private String path;
    private String method;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ApiResourceEntity getResource() {
        return resource;
    }

    public void setResource(ApiResourceEntity resource) {
        this.resource = resource;
    }
}
