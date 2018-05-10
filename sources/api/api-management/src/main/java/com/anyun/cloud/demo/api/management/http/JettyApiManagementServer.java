package com.anyun.cloud.demo.api.management.http;

import com.anyun.cloud.demo.api.management.core.distributed.management.DistributedManagementService;
import com.anyun.cloud.demo.api.management.module.HttpApiServerBindingModule;
import com.anyun.common.lang.http.AbstractJettyApiServer;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 29/05/2017
 */
@Singleton
public class JettyApiManagementServer extends AbstractJettyApiServer {
    private DistributedManagementService distributedManagementService;

    @Inject
    public JettyApiManagementServer(ZookeeperClient zookeeperClient,
                                    DistributedManagementService distributedManagementService,
                                    @Named(HttpApiServerBindingModule.NAMED_MGR_SERVLET_HANDLER)
                                            ServletHandler apiHandler,
                                    @Named(HttpApiServerBindingModule.NAMED_MGR_SERVLETS)
                                            List apiProcessServlet) {
        super(zookeeperClient, apiHandler, apiProcessServlet);
        this.distributedManagementService = distributedManagementService;
    }

    @Override
    protected void initServerConfig() throws Exception {
        ApiManagementServerConfigEntity config = distributedManagementService.getManagementApiServerConfig();
        setConfig(config.asConfig());
//        getServerInstance().setErrorHandler(new DefaultManagerApiErrorHandler());
    }

    @Override
    protected void registToCluster() throws Exception {
        String uid = distributedManagementService.regist();
//        Injector inject = InjectorsBuilder.getBuilder().getInjector();
//        Key<ScheduledExecutorServiceRunner> key =
//                Key.get(ScheduledExecutorServiceRunner.class, Names.named("TTL-UPDATER"));
//        TtlUpdater runner = (TtlUpdater) inject.getInstance(key);
//        runner.setNodeId(uid);
//        runner.start();
    }
}
