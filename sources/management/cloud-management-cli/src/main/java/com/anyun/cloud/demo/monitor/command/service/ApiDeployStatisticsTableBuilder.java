package com.anyun.cloud.demo.monitor.command.service;

import com.anyun.cloud.demo.common.etcd.GsonUtil;
import com.anyun.cloud.demo.common.etcd.client.HttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionNode;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionResponse;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiResourceEntity;
import com.anyun.cloud.demo.monitor.shell.AnsiConstants;
import com.anyun.common.lang.StringUtils;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModelBuilder;

import java.util.Date;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public class ApiDeployStatisticsTableBuilder implements StatisticsTableBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiDeployStatisticsTableBuilder.class);
    private static final String PATH_ETCD_API_DEPLOY = "/keys/api/deploy";
    private static final String PATH_API = "/anyuncloud/api";
    private HttpRestfullyApiClient etcApiClient;
    private ZookeeperClient zk;
    private GsonUtil gson;

    @Inject
    public ApiDeployStatisticsTableBuilder(@Named("etcd") HttpRestfullyApiClient etcApiClient, ZookeeperClient zk) {
        this.etcApiClient = etcApiClient;
        this.zk = zk;
        gson = GsonUtil.getUtil();
    }

    @Override
    public String build(String... args) throws Exception {
        String json = etcApiClient.get(PATH_ETCD_API_DEPLOY, null);
        EtcdActionResponse response = gson.getReponseEntity(json);
        TableModelBuilder<String> modelBuilder = getHeaderTableModeBuilder();
        for (EtcdActionNode node : response.getActionNode().getActionNodes()) {
            String spaceId = node.getKey().substring(response.getActionNode().getKey().length() + 1);
            String spacePath = PATH_ETCD_API_DEPLOY + "/" + spaceId;
            String baseUrl = gson.getReponseEntity(etcApiClient.get(spacePath, null))
                    .getActionNode().getNodeValueByName("url");
            String resourcePath = PATH_ETCD_API_DEPLOY + "/" + spaceId + "/resources";
            response = gson.getReponseEntity(etcApiClient.get(resourcePath, null));
            buildResourceTableMode(spaceId, baseUrl, modelBuilder, response);
        }
        TableBuilder tableBuilder = new TableBuilder(modelBuilder.build());
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        return tableBuilder.build().render(200);
    }


    private int queryApiDeployNodesCountById(String id) throws Exception {
        String zkPath = PATH_API + "/" + id;
        if (!zk.exist(zkPath)) {
            LOGGER.debug("API [{}] not deploy", id);
            return 0;
        }
        List<String> deplpyNodeNames = zk.getChildren(zkPath);
        if (deplpyNodeNames == null || deplpyNodeNames.isEmpty()) {
            LOGGER.debug("API [{}] not deploy", id);
            return 0;
        }
        LOGGER.debug("API [{}] deployed nodes: {}", id, deplpyNodeNames.toString());
        return deplpyNodeNames.size();
    }

    private void buildResourceTableMode(String spaceId, String baseUrl,
                                        TableModelBuilder<String> modelBuilder,
                                        EtcdActionResponse response) throws Exception {
        for (EtcdActionNode node : response.getActionNode().getActionNodes()) {
            ApiResourceEntity resourceEntity = gson.getGson().fromJson(node.getValue(), ApiResourceEntity.class);
            int keyIndex = node.getKey().lastIndexOf("/") + 1;
            String resourceId = node.getKey().substring(keyIndex, node.getKey().length());
            int count = queryApiDeployNodesCountById(resourceId);
            modelBuilder.addRow()
                    .addValue(spaceId)
                    .addValue(baseUrl)
                    .addValue("")
                    .addValue(resourceId)
                    .addValue(resourceEntity.getPath())
                    .addValue(resourceEntity.getMethod().toUpperCase())
                    .addValue(StringUtils.formatDate(new Date(resourceEntity.getDeployDateTime()), null))
                    .addValue("")
                    .addValue(count == 0 ? "NO ACTIVE!" : Integer.valueOf(count).toString())
                    .addValue("0")
                    .addValue("0");
        }
    }

    private TableModelBuilder<String> getHeaderTableModeBuilder() {
        TableModelBuilder<String> builder = new TableModelBuilder<>();
        builder.addRow()
                .addValue("## SPACE ID ##")
                .addValue("## BASE URL ##")
                .addValue("#")
                .addValue("## API ID ##")
                .addValue("## PATH ##")
                .addValue("## METHOD ##")
                .addValue("## DEPLOY DATETIME ##")
                .addValue("#")
                .addValue("## ACTIVE NODES ##")
                .addValue("## ACCESS COUNT ##")
                .addValue("## ERROR COUNT ##");
        return builder;
    }
}
