package com.anyun.common.lang.http;

import com.anyun.common.lang.zookeeper.ZookeeperClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 23/05/2017
 */
public abstract class AbstractJettyApiServer implements ApiServer<Server> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJettyApiServer.class);
    private ServerConfig config;
    private CountDownLatch countDownLatch;
    private Server server;
    private ZookeeperClient zookeeperClient;
    private ServletHandler apiHandler;
    private JettyServerThreadRunnable runnable;
    private List<ServletMapping> apiProcessServlets;
    private ServerStatus status;

    public AbstractJettyApiServer(
            ZookeeperClient zookeeperClient,
            ServletHandler apiHandler,
            List<ServletMapping> apiProcessServlets) {
        this.zookeeperClient = zookeeperClient;
        this.apiHandler = apiHandler;
        this.apiProcessServlets = apiProcessServlets;
        status = new ServerStatus();
    }

    protected abstract void initServerConfig() throws Exception;

    protected abstract void registToCluster() throws Exception;

    @Override
    public void start() throws Exception {
        if (runnable != null) {
            LOGGER.error("Api server is running");
            throw new Exception("Management api server is running");
        }
        if (apiProcessServlets == null || apiProcessServlets.isEmpty())
            throw new Exception("process servlets in not set");
        countDownLatch = new CountDownLatch(1);
        server = new Server();
        LOGGER.info("Release a api server instance");
        ServerConnector http = new ServerConnector(server);
        initServerConfig();
        http.setHost(config.getHost());
        LOGGER.info("Bind api server host to [{}]", config.getHost());
        http.setPort(config.getPort());
        LOGGER.info("Bind api server port to [{}]", config.getPort());
        http.setIdleTimeout(config.getIdleTimeout());
            server.addConnector(http);
        for (ServletMapping servlet : apiProcessServlets) {
            String servletMappingPath = servlet.getApiServletMappingPath() + "/*";
            apiHandler.addServletWithMapping(servlet.getApiProcessServlet(), servletMappingPath);
            LOGGER.info("Servlet class [{}] is mapping to path [{}]",
                    servlet.getApiProcessServlet().getCanonicalName(), servletMappingPath);
        }
        server.setHandler(apiHandler);
        runnable = new JettyServerThreadRunnable(this);
        runnable.start();
        LOGGER.info("Waiting for api server start");
        countDownLatch.await();
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("Restarting api server instance..............");
        server.stop();
        apiHandler.setServletMappings(null);
        runnable = null;
        zookeeperClient.stop();
    }

    @Override
    public void restart() throws Exception {
        stop();
        start();
    }

    @Override
    public ServerStatus getStatus() throws Exception {
        return status;
    }

    @Override
    public Server getServerInstance() {
        return server;
    }

    @Override
    public ServerConfig getConfig() {
        return config;
    }

    public void setConfig(ServerConfig config) {
        this.config = config;
    }

    /**
     * @auth TwitchGG <twitchgg@yahoo.com>
     * @since 1.0.0 on 23/05/2017
     */
    public static class JettyServerThreadRunnable implements Runnable {
        private static final Logger LOGGER = LoggerFactory.getLogger(ApiServer.class);
        private AbstractJettyApiServer server;
        private Thread thread;

        public JettyServerThreadRunnable(AbstractJettyApiServer server) {
            this.thread = new Thread(this);
            this.server = server;
        }

        /**
         *
         */
        @Override
        public void run() {
            try {
                long readyTime = System.currentTimeMillis();
                server.status.setRunning(false);
                server.server.start();
                server.countDownLatch.countDown();
                LOGGER.info("Http api server start success");

                server.status.setRunning(true);
                long currentTime = System.currentTimeMillis();
                server.status.setStartupTime(new Date(currentTime));
                server.status.setStartMillisecond(currentTime - readyTime);
                server.zookeeperClient.start();
                server.registToCluster();
                if (server.config.isJoinServerThread())
                    server.server.join();
            } catch (Exception e) {
                LOGGER.error("Http api server start error [{}]", e.getMessage(), e);
                System.exit(1);
            }
        }

        public void start() {
            thread.start();
        }
    }
}
