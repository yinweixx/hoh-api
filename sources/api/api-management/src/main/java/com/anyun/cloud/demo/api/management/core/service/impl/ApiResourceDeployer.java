package com.anyun.cloud.demo.api.management.core.service.impl;

import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiEntity;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;
import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.common.lang.HashIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public class ApiResourceDeployer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiResourceDeployer.class);
    private static final String PATH_API_DEPLOY = "/keys/api/deploy";
    private ApiEntity api;
    private HttpRestfullyApiClient apiClient;
    private String baseUrl;
    private String appNameHash;
    private String appPath;
    private String baseResDescPath;
    private long current;

    public ApiResourceDeployer(HttpRestfullyApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResourceDeployer withApi(ApiEntity api) {
        this.api = api;
        baseUrl = api.getBaseUrl().startsWith("/") ? api.getBaseUrl() : "/" + api.getBaseUrl();
        appNameHash = HashIdGenerator.generate(baseUrl);
        appPath = "/" + appNameHash;
        return this;
    }

    public void deploy() throws Exception {
        current = System.currentTimeMillis();
        deployAppToEtcd();
        for (ApiResourceEntity resource : api.getResources()) {
            deployResToEtcd(resource);
        }
    }

    private void deployAppToEtcd() throws Exception {
        apiClient.put(PATH_API_DEPLOY + appPath + "/url", buildBodyContent(api.getBaseUrl()), null);
        apiClient.put(PATH_API_DEPLOY + appPath + "/title", buildBodyContent(api.getTitle()), null);
        apiClient.put(PATH_API_DEPLOY + appPath + "/version", buildBodyContent(api.getVersion()), null);
        apiClient.put(PATH_API_DEPLOY + appPath + "/desc", buildBodyContent(api.getDescription()), null);
        baseResDescPath = PATH_API_DEPLOY + appPath + "/resources";
    }

    private String buildBodyContent(String body) {
        StringBuilder sb = new StringBuilder("value=");
        sb.append(body);
        return sb.toString();
    }

    private void deployResToEtcd(ApiResourceEntity resource) throws Exception {
        resource.setDeployDateTime(current);
        String name = baseUrl + resource.getPath();
        String resourceHash = HashIdGenerator.generate(name);
        LOGGER.debug("App [{}] Resource Name [{}],Hash [{}]", appNameHash, name, resourceHash);
        String resourcePath = baseResDescPath + "/" + resourceHash;
        StringBuilder body = new StringBuilder("value=");
        body.append(GsonUtil.getUtil().toJson(resource));
        String resp = apiClient.put(resourcePath, body.toString(), null);
        LOGGER.debug("Resource add response: {}", resp);
    }
}
