package com.anyun.cloud.management.web.thymeleaf;

import com.anyun.cloud.management.web.common.ResourceResolver;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafContext;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafControllerResolver;
import com.anyun.cloud.management.web.server.WebServerOptions;
import com.anyun.common.lang.options.ApplicationOptions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.Writer;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
@Singleton
public class DefaultThymeleafContext implements ThymeleafContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultThymeleafContext.class);
    private ClassLoaderTemplateResolver templateResolver;
    private ThymeleafControllerResolver controllerResolver;
    private TemplateEngine templateEngine;
    private ResourceResolver resourceResolver;
    private ClassLoader thymeleafControllerClassloader;

    @Inject
    public DefaultThymeleafContext(ApplicationOptions options,
                                   ThymeleafControllerClassloaderBuilder thymeleafControllerClassloaderBuilder,
                                   ThymeleafControllerResolver controllerResolver,
                                   ResourceResolver resourceResolver) throws Exception {
        this.controllerResolver = controllerResolver;
        this.resourceResolver = resourceResolver;
        initThymeleafContext(options);
    }

    private void initThymeleafContext(ApplicationOptions options) throws Exception {
        thymeleafControllerClassloader = controllerResolver.getThymeleafControllerClassloader();
        templateResolver = new ClassLoaderTemplateResolver(thymeleafControllerClassloader);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        if (!options.getCommandLine().hasOption(WebServerOptions.WEB_DEPLOY_DIR))
            throw new Exception("Option [" + WebServerOptions.WEB_DEPLOY_DIR + "] not set");
        templateResolver.setPrefix("/" + DIR_TEMPLATE);
        templateResolver.setSuffix(".html");
        if (options.getCommandLine().hasOption(WebServerOptions.TEMPLATE_CACHE_TTL)) {
            templateResolver.setCacheable(true);
            templateResolver.setCacheTTLMs(Long.valueOf(options.getCommandLine().getOptionValue(
                    WebServerOptions.TEMPLATE_CACHE_TTL)));
        } else
            templateResolver.setCacheable(false);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    public ThymeleafController resolveControllerForRequest(HttpServletRequest request) {
        return controllerResolver.resolve(request);
    }

    @Override
    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    @Override
    public void templateProcess(WebContext context, String templateURI, Writer writer) throws Exception {
        try {
            templateEngine.process(templateURI, context, writer);
        } catch (Exception ex) {
            LOGGER.error("Template engine process error: {}", ex.getMessage(), ex);
            throw new Exception("Template engine process error");
        }
    }

    @Override
    public ClassLoader getThymeleafControllerClassloader() {
        return thymeleafControllerClassloader;
    }
}
