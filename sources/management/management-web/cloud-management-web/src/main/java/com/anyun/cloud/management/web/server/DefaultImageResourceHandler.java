package com.anyun.cloud.management.web.server;

import com.anyun.cloud.management.web.common.ResourceResolver;
import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafContext;
import com.anyun.common.lang.bean.InjectorsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by twitchgg on 12/07/2017.
 */
public class DefaultImageResourceHandler implements ResourceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultImageResourceHandler.class);
    private static final String[] RESOURCE_IMG = {
            ".jpg",
            ".jpe",
            ".png",
            ".gif",
            ".ico"
    };
    private ResourceResolver resourceResolver;

    public DefaultImageResourceHandler() {
        this.resourceResolver = InjectorsBuilder.getBuilder()
                .getInstanceByType(ThymeleafContext.class).getResourceResolver();
    }

    @Override
    public void process(ServletRequest request, ServletResponse response) throws IOException {
        byte[] resourceBytes = resourceResolver.resolveResourceBytes((HttpServletRequest) request);
        response.setContentType(getResourceContentType(request));
        response.setContentLength(resourceBytes.length);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(resourceBytes);
        outputStream.flush();
    }

    @Override
    public boolean isResource(ServletRequest request) {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        String resource = requestURI;
        for (String suffix : RESOURCE_IMG) {
            if (resource.toLowerCase().endsWith(suffix))
                return true;
        }
        return false;
    }

    private String getResourceContentType(ServletRequest request) {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        int lastDotIndex = requestURI.lastIndexOf(".") + 1;
        String contentType = "image/" + requestURI.substring(lastDotIndex, requestURI.length());
        LOGGER.debug("Image content type [{}]", contentType);
        return contentType;
    }
}
