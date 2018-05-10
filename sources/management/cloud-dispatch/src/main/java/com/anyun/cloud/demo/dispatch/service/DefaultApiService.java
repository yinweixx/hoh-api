package com.anyun.cloud.demo.dispatch.service;

import com.anyun.cloud.demo.dispatch.service.entity.CloudResourceEntity;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class DefaultApiService implements ApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApiService.class);
    private static final String PATH_ETCD_API_DEPLOY = "/keys/api/deploy";
    private Etcd etcd;
    private Zk zk;

    @Inject
    public DefaultApiService(Zk zk, Etcd etcd) {
        this.zk = zk;
        this.etcd = etcd;
    }

    @Override
    public List<String> getNotDeployedServiceIds() throws Exception {
        List<String> notDeployServiceNames = new ArrayList<>();
        List<CloudResourceEntity> cloudResourceEntities = etcd.getDeployedApiResources();
        List<String> deployedApiServiceNames = zk.getDeployedApiServiceNames();
        LOGGER.debug("Must deploy service size: {},deployed service size: {}",
                cloudResourceEntities.size(), deployedApiServiceNames.size());
        for (CloudResourceEntity entity : cloudResourceEntities) {
            String resourceId = entity.getResourceId();
            boolean flag = false;
            for (String deployedName : deployedApiServiceNames) {
                if (deployedName.equals(resourceId)) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                LOGGER.debug("Not deploy service: {}[{}]", entity.getEntity().getPath(), entity.getResourceId());
                notDeployServiceNames.add(entity.getResourceId());
            }
        }
        return notDeployServiceNames;
    }

    @Override
    public void deployServices() throws Exception {

    }
}
