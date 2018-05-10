package com.anyun.cloud.demo.api.node.core;

import com.anyun.cloud.demo.api.node.core.common.ApiCache;
import com.anyun.cloud.demo.api.node.core.common.ApiDeployer;
import com.anyun.cloud.demo.api.node.core.common.ApiFinder;
import com.anyun.cloud.demo.api.node.core.common.NodeApiComponent;
import com.anyun.cloud.demo.api.node.core.common.entity.ApiDeployEntity;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public class DefaultNodeApiComponent implements NodeApiComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNodeApiComponent.class);
    private static final int DEPLOY_COUNT_MIN = 3;
    private ApiFinder apiFinder;
    private ApiDeployer deployer;
    private ApiCache apiCache;

    @Inject
    public DefaultNodeApiComponent(ApiFinder apiFinder, ApiDeployer deployer, ApiCache apiCache) {
        this.apiFinder = apiFinder;
        this.deployer = deployer;
        this.apiCache = apiCache;
    }

    @Override
    public ApiResourceEntity findResource(String id, String method) throws Exception {
        ApiResourceEntity apiResourceEntity = apiCache.findApiResourceId(id);
        if (apiResourceEntity == null) {
            apiResourceEntity = apiFinder.findApiResourceDeployInfo(id);
            apiCache.putResource(id, apiResourceEntity);
        }
        if (apiResourceEntity == null)
            return null;
        if (!method.toLowerCase().equals(apiResourceEntity.getMethod().toLowerCase())) {
            LOGGER.debug("Found resource [{}],but resource method is not match [{}]", id, method);
            return null;
        }
        return apiResourceEntity;
    }

    @Override
    public boolean mustDeploy(String id) throws Exception {
        int deployCount = apiFinder.findApiDeployNodesById(id).size();
        LOGGER.debug("Resource [{}] deploy node size [{}]", id, deployCount);
        if (deployCount < DEPLOY_COUNT_MIN)
            return true;
        return false;
    }

    @Override
    public void deployResource(ApiDeployEntity deployEntity) throws Exception {
        deployer.deploy(deployEntity);
    }
}
