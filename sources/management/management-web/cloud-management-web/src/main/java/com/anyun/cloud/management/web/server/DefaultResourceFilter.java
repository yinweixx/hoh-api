package com.anyun.cloud.management.web.server;

import com.anyun.common.lang.bean.InjectorsBuilder;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public class DefaultResourceFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultResourceFilter.class);
    private ResourceHandler textResourceHandler;
    private ResourceHandler imageResourceHandler;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.textResourceHandler = InjectorsBuilder.getBuilder().getInjector()
                .getInstance(Key.get(ResourceHandler.class, Names.named("text")));
        this.imageResourceHandler = InjectorsBuilder.getBuilder().getInjector()
                .getInstance(Key.get(ResourceHandler.class, Names.named("image")));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (textResourceHandler.isResource(request)) {
            textResourceHandler.process(request, response);
        } else if (imageResourceHandler.isResource(request))
            imageResourceHandler.process(request, response);
        else {
            chain.doFilter(request, response);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
