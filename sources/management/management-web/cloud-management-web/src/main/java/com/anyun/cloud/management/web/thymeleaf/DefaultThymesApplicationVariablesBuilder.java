package com.anyun.cloud.management.web.thymeleaf;

import com.anyun.cloud.management.web.common.thymeleaf.ThymesApplicationVariables;
import com.anyun.cloud.management.web.common.thymeleaf.ThymesApplicationVariablesBuilder;
import com.anyun.common.lang.options.ApplicationOptions;
import com.google.inject.Inject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public class DefaultThymesApplicationVariablesBuilder implements ThymesApplicationVariablesBuilder {
    private ApplicationOptions options;

    @Inject
    public DefaultThymesApplicationVariablesBuilder(ApplicationOptions options) {
        this.options = options;
    }

    @Override
    public ThymesApplicationVariables build(ServletContext servletContext, HttpServletRequest request) {
        ThymesApplicationVariables variables = new ThymesApplicationVariables();
        return variables;
    }
}
