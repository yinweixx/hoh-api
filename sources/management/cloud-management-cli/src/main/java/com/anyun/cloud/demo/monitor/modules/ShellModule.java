package com.anyun.cloud.demo.monitor.modules;

import com.anyun.cloud.demo.monitor.command.service.ApiDeployStatisticsTableBuilder;
import com.anyun.cloud.demo.monitor.command.service.ApiNodesRuntimeStatisticsTableBuilder;
import com.anyun.cloud.demo.monitor.command.service.NodesRuntimeStatisticsTableBuilder;
import com.anyun.cloud.demo.monitor.command.service.StatisticsTableBuilder;
import com.google.inject.name.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public class ShellModule extends AbstractCloudModules {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShellModule.class);

    @Override
    protected void init() throws Exception {
        bindTableBuilder(NodesRuntimeStatisticsTableBuilder.class);
        bindTableBuilder(ApiDeployStatisticsTableBuilder.class);
        bindTableBuilder(ApiNodesRuntimeStatisticsTableBuilder.class);
    }

    private void bindTableBuilder(Class<? extends StatisticsTableBuilder> clazz) {
        bind(StatisticsTableBuilder.class)
                .annotatedWith(Names.named(clazz.getSimpleName())).to(clazz);
    }
}
