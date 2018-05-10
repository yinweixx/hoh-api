package com.anyun.cloud.demo.dispatch.service;

import com.anyun.cloud.demo.dispatch.service.entity.CloudResourceEntity;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public interface Etcd {
    List<CloudResourceEntity> getDeployedApiResources() throws Exception;
}
