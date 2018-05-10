package com.anyun.cloud.demo.api.node.core.common;

import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
public interface ApiCache {
    ApiResourceEntity findApiResourceId(String id);

    void putResource(String id, ApiResourceEntity entity);
}
