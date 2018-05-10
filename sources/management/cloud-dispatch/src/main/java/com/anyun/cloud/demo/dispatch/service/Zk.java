package com.anyun.cloud.demo.dispatch.service;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public interface Zk {
    List<String> getDeployedApiServiceNames() throws Exception;
}
