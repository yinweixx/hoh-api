package com.anyun.cloud.demo.monitor.command;

import com.anyun.cloud.demo.monitor.command.service.NodesRuntimeStatisticsTableBuilder;
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
 * @since 1.0.0 on 07/06/2017
 */
@Component
public class NodeMonitorCommands implements CommandMarker {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeMonitorCommands.class);
    private static final String STATUS_RUNNING = "running";

    public NodeMonitorCommands() throws Exception {
    }

    @CliAvailabilityIndicator({
            "node statistics"
    })
    public boolean isSimpleAvailable() {
        return true;
    }

    @CliCommand(value = "node statistics", help = "平台节点运行情况统计")
    public void statistics(
            @CliOption(key = {"status"}, mandatory = true, help = "要统计的节点状态(running|all)") final String status
    ) throws Exception {
        if (status.equals(STATUS_RUNNING)) {
            String content =
                    StatisticsTableBuilder.getTableBuilderByClass(NodesRuntimeStatisticsTableBuilder.class).build(null);
            ShellPrinter.print(content);
        } else {
            ShellPrinter.error("Unsupported status [" + status + "]");
        }
    }
}
