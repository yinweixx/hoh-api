package com.anyun.cloud.demo.monitor.command;

import com.anyun.cloud.demo.monitor.command.service.ApiDeployStatisticsTableBuilder;
import com.anyun.cloud.demo.monitor.command.service.ApiNodesRuntimeStatisticsTableBuilder;
import com.anyun.cloud.demo.monitor.command.service.StatisticsTableBuilder;
import com.anyun.cloud.demo.monitor.shell.ShellPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
@Component
public class ApiMonitorCommands implements CommandMarker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiMonitorCommands.class);

    @CliAvailabilityIndicator({
            "api deploy",
            "api nodes"
    })

    public boolean isSimpleAvailable() {
        return true;
    }

    @CliCommand(value = "api deploy", help = "平台API部署情况统计")
    public void apiDeploy(
            @CliOption(key = {"filter"}, mandatory = false, help = "API过滤器") final String filter
    ) throws Exception {
        String content =
                StatisticsTableBuilder.getTableBuilderByClass(ApiDeployStatisticsTableBuilder.class).build(filter);
        ShellPrinter.print(content);
    }

    @CliCommand(value = "api nodes", help = "平台API节点运行情况统计")
    public void apiNodes(
            @CliOption(key = {"filter"}, mandatory = false, help = "API节点过滤器") final String filter
    ) throws Exception {
        String content =
                StatisticsTableBuilder.getTableBuilderByClass(ApiNodesRuntimeStatisticsTableBuilder.class).build(filter);
        ShellPrinter.print(content);
    }
}
