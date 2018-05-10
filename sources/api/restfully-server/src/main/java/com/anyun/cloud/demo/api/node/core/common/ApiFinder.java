package com.anyun.cloud.demo.api.node.core.common;

import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiEntity;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public interface ApiFinder {

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    List<String> findApiDeployNodesById(String id) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    ApiResourceEntity findApiResourceDeployInfo(String id) throws Exception;
}
