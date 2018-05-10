package com.anyun.cloud.demo.api.node.core.common;

import com.anyun.cloud.demo.api.node.core.common.entity.ApiDeployEntity;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public interface ApiDeployer {

    /**
     *
     * @param deployEntity
     * @throws Exception
     */
    void deploy(ApiDeployEntity deployEntity) throws Exception;
}
