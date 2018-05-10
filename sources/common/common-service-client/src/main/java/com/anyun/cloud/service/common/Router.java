package com.anyun.cloud.service.common;

import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public interface Router {
    /**
     * @param resourceId
     * @param headers
     * @param requestBody
     * @param responseType
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T route(String resourceId, Map<String, String> headers, String requestBody, Class<T> responseType) throws Exception;
}