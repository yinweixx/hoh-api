package com.anyun.cloud.demo.api.management.module;

import com.anyun.cloud.demo.api.management.http.JettyApiManagementServer;
import com.anyun.common.lang.http.AbstractApiCallbackBindModule;
import com.anyun.common.lang.http.ApiServer;
import com.anyun.common.lang.http.DefaultApiServlet;
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
public class HttpApiServerBindingModule extends AbstractModule {
    public static final String NAMED_CONFIG_HTTP = "config.http.api";
    public static final String NAMED_MGR_SERVLET_HANDLER = "config.http.api.servlet.handler";
    public static final String NAMED_MGR_SERVLETS = "config.http.api.servlets";

    public static final String NAMED_HTTP_MAPPING_API = "/api";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpApiServerBindingModule.class);

    @Override
    protected void configure() {
        bind(AbstractApiCallbackBindModule.class).annotatedWith(Names.named(ApiServer.NAMED_CALLBACK + NAMED_HTTP_MAPPING_API))
                .to(ManagementApiCallbackBindModule.class);
        LOGGER.info("Bind management api callback to: {}", ManagementApiCallbackBindModule.class);

        ServletHandler servletHandler = new ServletHandler();
        bind(ServletHandler.class).annotatedWith(Names.named(NAMED_MGR_SERVLET_HANDLER)).toInstance(servletHandler);
        LOGGER.info("Bind management server servlet handler to: {}", ServletHandler.class.getCanonicalName());

        List<ServletMapping> servletMappings = new LinkedList<>();
        servletMappings.add(new ServletMapping(NAMED_HTTP_MAPPING_API, DefaultApiServlet.class));
        bind(List.class).annotatedWith(Names.named(NAMED_MGR_SERVLETS)).toInstance(servletMappings);

        bind(ApiServer.class).to(JettyApiManagementServer.class);
        LOGGER.info("Bind management server implement to: {}", JettyApiManagementServer.class.getCanonicalName());
    }
}
