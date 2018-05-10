package com.anyun.cloud.demo.common.etcd.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public interface HttpRestfullyApiClient {

    /**
     *
     * @param pathSegments
     * @param queryParameters
     * @return
     * @throws IOException
     */
    String get(String pathSegments, Map<String, List<Object>> queryParameters) throws IOException;

    /**
     *
     * @param pathSegments
     * @param value
     * @param type
     * @return
     * @throws IOException
     */
    String post(String pathSegments, String value, String type) throws IOException;

    /**
     *
     * @param pathSegments
     * @param value
     * @param type
     * @return
     * @throws IOException
     */
    String put(String pathSegments, String value, String type) throws IOException;

    /**
     *
     * @param pathSegments
     * @param queryParameters
     * @param value
     * @param type
     * @return
     * @throws IOException
     */
    String del(String pathSegments, Map<String, List<Object>> queryParameters,
               String value, String type) throws IOException;
}
