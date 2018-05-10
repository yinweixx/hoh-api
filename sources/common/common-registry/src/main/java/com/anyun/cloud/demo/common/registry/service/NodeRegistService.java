package com.anyun.cloud.demo.common.registry.service;

import com.anyun.cloud.demo.common.registry.entity.NodeEntity;
import com.anyun.cloud.demo.common.registry.entity.NodeType;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public interface NodeRegistService {
    String PATH_NODE = "/anyuncloud/nodes";

    /**
     * @param nodeEntity
     * @return
     * @throws Exception
     */
    String registNode(NodeEntity nodeEntity) throws Exception;

    /**
     *
     * @param nodeTypes
     * @return
     * @throws Exception
     */
    String registNode(NodeType... nodeTypes) throws Exception;
}
