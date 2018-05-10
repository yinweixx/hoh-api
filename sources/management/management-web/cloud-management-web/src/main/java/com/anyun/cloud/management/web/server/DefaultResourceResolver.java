package com.anyun.cloud.management.web.server;

import com.anyun.cloud.management.web.common.ResourceResolver;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafContext;
import com.anyun.common.lang.Resources;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.options.ApplicationOptions;
import com.google.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public class DefaultResourceResolver implements ResourceResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultResourceResolver.class);

    @Override
    public InputStream resolve(HttpServletRequest request) throws IOException {
        ClassLoader classLoader = InjectorsBuilder.getBuilder().getInstanceByType(ThymeleafContext.class).getThymeleafControllerClassloader();
        String resourceURI = request.getRequestURI().substring(1);
        LOGGER.debug("Resource classpath url [{}]", resourceURI);
        return Resources.getResourceAsStream(classLoader, resourceURI);
    }

    @Override
    public byte[] resolveResourceBytes(HttpServletRequest request) throws IOException {
        InputStream inputStream = resolve(request);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        return bytes;
    }
}
