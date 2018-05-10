package com.anyun.cloud.demo.monitor.command.service;

import com.anyun.cloud.demo.common.registry.entity.NodeEntity;
import com.anyun.cloud.demo.common.registry.entity.NodeNetworkEntity;
import com.anyun.cloud.demo.common.registry.entity.NodeType;
import com.anyun.common.lang.StringUtils;
import com.anyun.common.lang.json.GsonUtil;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModelBuilder;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 07/06/2017
 */
public class NodesRuntimeStatisticsTableBuilder implements StatisticsTableBuilder {
    public static final String ZK_PATH_NODE = "/anyuncloud/nodes";
    protected ZookeeperClient zk;

    @Inject
    public NodesRuntimeStatisticsTableBuilder(ZookeeperClient zk) {
        this.zk = zk;
    }

    @Override
    public String build(String... args) throws Exception {
        if (!zk.exist(ZK_PATH_NODE))
            return "not node is running";
        List<String> children = zk.getChildren(ZK_PATH_NODE);
        if (children == null || children.isEmpty())
            return "not node is running";
        TableModelBuilder<String> tmbuilder = getModelBuilder();
        for (String child : children) {
            NodeEntity node = getNodeEntity(child);
            if (haveHiddenType(node.getNodeType()))
                continue;
            tmbuilder.addRow().addValue(node.getUid());
            StringBuilder sb = new StringBuilder();
            for (NodeType nodeType : node.getNodeType()) {
                sb.append(",").append(nodeType.name());
            }
            long upstart = node.getUpstartTimestamp();
            tmbuilder.addValue(sb.toString().substring(1).replace(",", "\n"))
                    .addValue(StringUtils.formatDate(new Date(upstart), null));
            String nwString = getNetworkString(node);
            tmbuilder.addValue(nwString);
        }
        TableBuilder tableBuilder = new TableBuilder(tmbuilder.build());
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        return tableBuilder.build().render(200);
    }

    protected String getNetworkString(NodeEntity node) {
        List<NodeNetworkEntity> nws = node.getNetworks();
        StringBuilder sb = new StringBuilder();
        for (NodeNetworkEntity nw : nws) {
            StringBuilder isb = new StringBuilder();
            for (String ip : nw.getIp()) {
                isb.append(",").append(ip);
            }
            sb.append("@").append(nw.getEtherName()).append(" ( ").append(isb.substring(1)).append(" )");
        }
        return sb.toString().substring(1).replace("@", "\n");
    }

    protected NodeEntity getNodeEntity(String child) throws Exception {
        String jsonContent = zk.getStringData(ZK_PATH_NODE + "/" + child);
        NodeEntity node = GsonUtil.getUtil().getGson().fromJson(jsonContent, NodeEntity.class);
        return node;
    }

    private TableModelBuilder<String> getModelBuilder() {
        TableModelBuilder<String> builder = new TableModelBuilder<String>()
                .addRow()
                .addValue("## DEVICE ID ##")
                .addValue("## DEVICE TYPE ##")
                .addValue("## SERVICE UP START TIME ##")
                .addValue("## NETWORK(s) ##");
        return builder;
    }

    private boolean haveHiddenType(List<NodeType> nodeTypes) {
        for (NodeType type : nodeTypes) {
            if (type.equals(NodeType.API_REST_NODE)
                    || type.equals(NodeType.SERVICE_NODE))
                return true;
        }
        return false;
    }
}
