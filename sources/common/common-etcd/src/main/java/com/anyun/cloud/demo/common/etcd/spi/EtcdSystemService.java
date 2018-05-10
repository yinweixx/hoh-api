package com.anyun.cloud.demo.common.etcd.spi;

import com.anyun.cloud.demo.common.etcd.response.VersionResponse;

/**
 *
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/17
 */
public interface EtcdSystemService {

    /**
     *
     * @return
     * @throws Exception
     */
    VersionResponse getVersion() throws Exception;
}
