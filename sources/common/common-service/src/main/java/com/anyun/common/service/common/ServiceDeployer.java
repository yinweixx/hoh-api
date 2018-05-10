package com.anyun.common.service.common;

import com.anyun.cloud.service.common.Service;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public interface ServiceDeployer {

    /**
     * @param deviceId
     * @param aClass
     * @throws Exception
     */
    void deploy(String deviceId, Class<? extends Service> aClass) throws Exception;
}
