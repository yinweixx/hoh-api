package com.anyun.cloud.demo.api.node.core;

import com.anyun.cloud.demo.api.node.core.common.ApiCache;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 09/06/2017
 */
@Singleton
public class DefaultApiCache implements ApiCache {
    private Map<String, ApiResourceEntity> resourcesCache;

    public DefaultApiCache() {
        this.resourcesCache = new HashMap<>();
    }

    @Override
    public ApiResourceEntity findApiResourceId(String id) {
        return resourcesCache.get(id);
    }

    @Override
    public void putResource(String id, ApiResourceEntity entity) {
        resourcesCache.put(id, entity);
    }
}
