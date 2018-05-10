package com.anyun.cloud.management.web.server;

import com.anyun.cloud.management.web.common.api.API;
import com.anyun.common.lang.StringUtils;
import com.anyun.common.lang.http.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/07/2017
 */
public abstract class AbstractRestfullyApiResourceHandler implements ResourceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestfullyApiResourceHandler.class);

    @Override
    public void process(ServletRequest request, ServletResponse response) throws IOException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        String apiVersion = (String) request.getServletContext().getAttribute("version.api.rest");
        String apiPath = requestURI.substring(("/api/" + apiVersion).length());
        String method = ((HttpServletRequest) request).getMethod();
        LOGGER.debug("api path [{}] [{}]", apiPath, method);
        String contentType = ((HttpServletRequest) request).getHeader("Content-Type");
        API api = new API();
        api.setContentType(contentType);
        api.setMethod(method);
        api.setPath(apiPath);
        String queryString = ((HttpServletRequest) request).getQueryString();
        if (!StringUtils.isEmpty(queryString))
            api.setQueryString(RequestUtil.getUriQueryParameters(queryString));
        api.setRequest((HttpServletRequest) request);
        process(api);
    }


    protected abstract void process(API api) throws IOException;

    @Override
    public boolean isResource(ServletRequest request) {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (requestURI.startsWith("/api")) {
            String apiVersion = (String) request.getServletContext().getAttribute("version.api.rest");
            if (requestURI.startsWith("/api/" + apiVersion + "/")) {
                LOGGER.debug("Version [{}]", apiVersion);
                return true;
            }
        }
        return false;
    }
}
