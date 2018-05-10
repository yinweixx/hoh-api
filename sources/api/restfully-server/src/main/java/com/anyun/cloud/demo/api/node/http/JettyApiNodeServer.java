package com.anyun.cloud.demo.api.node.http;

import com.anyun.cloud.demo.api.node.module.HttpBindingModule;
import com.anyun.cloud.demo.common.registry.entity.NodeType;
import com.anyun.cloud.demo.common.registry.service.NodeRegistService;
import com.anyun.common.lang.http.AbstractJettyApiServer;
import com.anyun.common.lang.http.ServerConfig;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/06/2017
 */
@Singleton
public class JettyApiNodeServer extends AbstractJettyApiServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JettyApiNodeServer.class);
    private NodeRegistService registService;
    private String deviceId;

    @Inject
    public JettyApiNodeServer(ZookeeperClient zookeeperClient,
                              ServletHandler apiHandler,
                              NodeRegistService registService,
                              @Named(HttpBindingModule.NAMED_MGR_SERVLETS) List apiProcessServlet) {
        super(zookeeperClient, apiHandler, apiProcessServlet);
        this.registService = registService;
    }

    @Override
    protected void initServerConfig() throws Exception {
        ServerConfig config = new ServerConfig();
        config.setPort(8080);
        setConfig(config);
    }

    @Override
    protected void registToCluster() throws Exception {
        deviceId = registService.registNode(NodeType.API_REST_NODE);
        LOGGER.debug("Regist device id: {}", deviceId);
    }

    public String getDeviceId() {
        return deviceId;
    }
}
