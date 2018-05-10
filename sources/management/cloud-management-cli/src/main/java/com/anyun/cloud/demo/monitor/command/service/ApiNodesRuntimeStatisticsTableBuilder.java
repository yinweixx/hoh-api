package com.anyun.cloud.demo.monitor.command.service;

import com.anyun.cloud.demo.common.registry.entity.NodeEntity;
import com.anyun.cloud.demo.common.registry.entity.NodeType;
import com.anyun.common.lang.StringUtils;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModelBuilder;

import java.util.Date;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
public class ApiNodesRuntimeStatisticsTableBuilder extends NodesRuntimeStatisticsTableBuilder {
    private TableModelBuilder<String> modelBuilder;

    @Inject
    public ApiNodesRuntimeStatisticsTableBuilder(ZookeeperClient zk) {
        super(zk);
    }

    @Override
    public String build(String... args) throws Exception {
        if (!zk.exist(ZK_PATH_NODE))
            return "not node is running";
        List<String> children = zk.getChildren(ZK_PATH_NODE);
        if (children == null || children.isEmpty())
            return "not node is running";
        modelBuilder = getNodeModelBuilder();
        for (String child : children) {
            NodeEntity node = getNodeEntity(child);
            if (!isApiNode(node.getNodeType()))
                continue;
            buildApiNodeStatus(node);
        }
        TableBuilder tableBuilder = new TableBuilder(modelBuilder.build());
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        return tableBuilder.build().render(300);
    }

    private void buildApiNodeStatus(NodeEntity node) throws Exception {
        String upstart = StringUtils.formatDate(new Date(node.getUpstartTimestamp()), null);
        modelBuilder.addRow()
                .addValue(node.getUid())
                .addValue(upstart)
                .addValue(getNetworkString(node))
                .addValue("0")
                .addValue("yyyy-MM-dd HH:mm:ss")
                .addValue("#.##%")
                .addValue("#.##%");
    }

    private boolean isApiNode(List<NodeType> nodeTypes) {
        for (NodeType type : nodeTypes) {
            if (type.equals(NodeType.API_REST_NODE))
                return true;
        }
        return false;
    }

    private TableModelBuilder<String> getNodeModelBuilder() {
        TableModelBuilder<String> builder = new TableModelBuilder<String>()
                .addRow()
                .addValue("## NODE ID ##")
                .addValue("## NODE UP START TIME ##")
                .addValue("## NETWORK(s) ##")
                .addValue("## API COUNT ##")
                .addValue("## LAST ACCESS DATETIME ##")
                .addValue("## CPU USAGE(%) ##")
                .addValue("## MEMORY USAGE(%) ##");
        return builder;
    }
}
