package com.anyun.cloud.demo.api.management.core.service.impl;

import com.anyun.cloud.demo.api.management.core.service.ApiManagementService;
import com.anyun.cloud.demo.api.management.core.service.ApiStatusEntity;
import com.anyun.cloud.demo.api.management.raml.RamlApiRamlParser;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiEntity;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/05/2017
 */
public class DefaultApiManagementServiceImpl implements ApiManagementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiManagementService.class);

    private RamlApiRamlParser ramlApiRamlParser;
    private HttpRestfullyApiClient apiClient;

    @Inject
    public DefaultApiManagementServiceImpl(RamlApiRamlParser ramlApiRamlParser, HttpRestfullyApiClient apiClient) {
        this.ramlApiRamlParser = ramlApiRamlParser;
        this.apiClient = apiClient;
    }

    @Override
    public String deploy(String zipFileName) throws Exception {
        File[] files = new File(zipFileName).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.toLowerCase().trim().endsWith(".raml"))
                    return true;
                return false;
            }
        });
        if (files == null || files.length == 0) {
            LOGGER.error("Not find api define file");
            throw new Exception("Not find api define file");
        }
        ApiResourceDeployer resourceDeployer = new ApiResourceDeployer(apiClient);
        for (File file : new File(zipFileName).listFiles()) {
            ApiEntity api = ramlApiRamlParser.withEncoding(RamlApiRamlParser.ENCODING).withRamlFile(file).buildApi();
            resourceDeployer.withApi(api).deploy();
        }
        return null;
    }

    @Override
    public void destroy(String rid) throws Exception {

    }

    @Override
    public void enable(String rid) throws Exception {

    }

    @Override
    public void disable(String rid) throws Exception {

    }

    @Override
    public ApiStatusEntity getApiStatus(String rid) throws Exception {
        return null;
    }

    @Override
    public List<ApiStatusEntity> getApiStatues() throws Exception {
        return null;
    }

    @Override
    public List<ApiStatusEntity> queryApiStatues(String query) throws Exception {
        return null;
    }
}
