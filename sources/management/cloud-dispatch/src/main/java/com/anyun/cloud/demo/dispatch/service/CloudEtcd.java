package com.anyun.cloud.demo.dispatch.service;

import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionNode;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionResponse;
import com.anyun.cloud.demo.common.etcd.spi.EtcdExtendService;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;
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
public class CloudEtcd implements Etcd {
    private static final String PATH_ETCD_API_DEPLOY = "/keys/api/deploy";
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudEtcd.class);
    private HttpRestfullyApiClient etcd;

    @Inject
    public CloudEtcd(EtcdExtendService etcd) {
        this.etcd = etcd.getClient();
    }

    @Override
    public List<CloudResourceEntity> getDeployedApiResources() throws Exception {
        List<CloudResourceEntity> cloudUnDeployResourceEntities = new ArrayList<>();
        String json = etcd.get(PATH_ETCD_API_DEPLOY, null);
        EtcdActionResponse response = GsonUtil.getUtil().getReponseEntity(json);
        for (EtcdActionNode node : response.getActionNode().getActionNodes()) {
            String spaceId = node.getKey().substring(response.getActionNode().getKey().length() + 1);
//            String spacePath = PATH_ETCD_API_DEPLOY + "/" + spaceId;
//            String baseUrl = GsonUtil.getUtil().getReponseEntity(etcd.get(spacePath, null))
//                    .getActionNode().getNodeValueByName("url");
            String resourcePath = PATH_ETCD_API_DEPLOY + "/" + spaceId + "/resources";
            response = GsonUtil.getUtil().getReponseEntity(etcd.get(resourcePath, null));

            for (EtcdActionNode resNode : response.getActionNode().getActionNodes()) {
                ApiResourceEntity resourceEntity = GsonUtil.getUtil().getGson()
                        .fromJson(resNode.getValue(), ApiResourceEntity.class);
                int keyIndex = resNode.getKey().lastIndexOf("/") + 1;
                String resourceId = resNode.getKey().substring(keyIndex, resNode.getKey().length());
                LOGGER.debug("Deploy resource id: {}", resourceId);
                cloudUnDeployResourceEntities.add(new CloudResourceEntity(resourceId, resourceEntity));
            }
        }
        return cloudUnDeployResourceEntities;
    }
}
