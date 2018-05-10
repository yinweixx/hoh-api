package com.anyun.cloud.management.web.common.thymeleaf;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public interface ThymesApplicationVariablesBuilder {

    /**
     *
     * @param servletContext
     * @param request
     * @return
     */
    ThymesApplicationVariables build(ServletContext servletContext, HttpServletRequest request);
}
