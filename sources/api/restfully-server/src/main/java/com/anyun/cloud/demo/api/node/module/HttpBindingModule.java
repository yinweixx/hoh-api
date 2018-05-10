package com.anyun.cloud.demo.api.node.module;

import com.anyun.cloud.demo.api.node.http.ApiNodeServlet;
import com.anyun.cloud.demo.api.node.http.JettyApiNodeServer;
import com.anyun.cloud.demo.api.node.http.ResourceCache;
import com.anyun.common.lang.http.ApiServer;
import com.anyun.common.lang.http.ServletMapping;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class HttpBindingModule extends AbstractModule {
    public static final String NAMED_MGR_SERVLETS = "config.http.api.servlets";

    public static final String NAMED_HTTP_MAPPING_API = "/api";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpBindingModule.class);

    @Override
    protected void configure() {
        bind(ServletHandler.class).toInstance(new ServletHandler());
        LOGGER.info("Bind http server servlet handler to: {}", ServletHandler.class.getCanonicalName());

        List<ServletMapping> servletMappings = new LinkedList<>();
        servletMappings.add(new ServletMapping(NAMED_HTTP_MAPPING_API, ApiNodeServlet.class));
        bind(List.class).annotatedWith(Names.named(NAMED_MGR_SERVLETS)).toInstance(servletMappings);

        bind(ApiServer.class).to(JettyApiNodeServer.class);
        LOGGER.info("Bind http server implement to: {}", JettyApiNodeServer.class.getCanonicalName());
        bind(ResourceCache.class).toInstance(new ResourceCache());
    }
}
