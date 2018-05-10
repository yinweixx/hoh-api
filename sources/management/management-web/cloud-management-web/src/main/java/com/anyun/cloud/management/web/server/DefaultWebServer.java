package com.anyun.cloud.management.web.server;

import com.anyun.cloud.management.web.common.HandlerListBuilder;
import com.anyun.cloud.management.web.common.WebAppConfig;
import com.anyun.cloud.management.web.common.WebAppConfigBuilder;
import com.anyun.cloud.management.web.common.WebServer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
@Singleton
public class DefaultWebServer implements WebServer {
    private WebAppConfig config;
    private Server server;
    private MBeanContainer mbContainer;
    private ServerThreadLooper serverThreadLooper;
    private HandlerListBuilder handlerListBuilder;

    @Inject
    public DefaultWebServer(WebAppConfigBuilder configBuilder,
                            HandlerListBuilder handlerListBuilder) throws Exception {
        this.config = configBuilder.build();
        this.handlerListBuilder = handlerListBuilder;
    }

    @Override
    public void start() throws Exception {
        if (server != null)
            throw new Exception("Web server is running");
        serverThreadLooper = new ServerThreadLooper();
        server = new Server(config.getPort());
        mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbContainer);
        server.setHandler(handlerListBuilder.build());
        server.start();
        serverThreadLooper.start();
    }

    @Override
    public void stop() throws Exception {
        if (!server.isStarted())
            throw new Exception("Web server is not started");
        serverThreadLooper.stop();
        if (!server.isStopped())
            server.stop();
        mbContainer = null;
        server = null;
    }

    public Server getServer() {
        return server;
    }

    public WebAppConfig getConfig() {
        return config;
    }

    private static class ServerThreadLooper implements Runnable {
        private Thread thread;
        private CountDownLatch latch;

        public ServerThreadLooper() {
            thread = new Thread(this);
        }

        public void start() {
            latch = new CountDownLatch(1);
            thread.start();
        }

        public void stop() {
            latch.countDown();
        }

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
