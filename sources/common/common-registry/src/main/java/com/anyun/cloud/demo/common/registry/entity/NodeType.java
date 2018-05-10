package com.anyun.cloud.demo.common.registry.entity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public enum NodeType {
    API_REST_NODE(1),
    SERVICE_NODE(2),
    MGR_NODE(3),
    BIND(4),
    ETCD(5),
    NATS(6),
    REDIS(7),
    ZOOKEEPER(8),
    LOAD_BALANCE(9),
    TEST_NODE(0),
    HOST_LXD(11),
    HOST_DOCKER(12);

    private int value;

    NodeType(int value) {
        this.value = value;
    }
}
