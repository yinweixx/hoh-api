package com.anyun.cloud.demo.api.management.core.distributed.management;

import com.anyun.cloud.demo.api.management.http.ApiManagementServerConfigEntity;
import com.anyun.cloud.demo.common.etcd.EtcdErrorResponseException;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/05/2017
 */
public interface DistributedManagementService {

    /**
     * @return
     * @throws EtcdErrorResponseException
     */
    ApiManagementServerConfigEntity getManagementApiServerConfig() throws EtcdErrorResponseException;

    /**
     * @throws Exception
     */
    String regist() throws Exception;
}
