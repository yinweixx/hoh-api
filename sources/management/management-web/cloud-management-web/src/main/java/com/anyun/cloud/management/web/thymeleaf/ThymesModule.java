package com.anyun.cloud.management.web.thymeleaf;

import com.anyun.cloud.management.web.common.ResourceFilter;
import com.anyun.cloud.management.web.common.ResourceResolver;
import com.anyun.cloud.management.web.common.thymeleaf.*;
import com.anyun.cloud.management.web.server.DefaultResourceFilter;
import com.anyun.cloud.management.web.server.DefaultResourceResolver;
import com.google.inject.AbstractModule;

import javax.servlet.Filter;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 21/06/2017
 */
public class ThymesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ThymeleafContext.class).to(DefaultThymeleafContext.class);
        configureFilters();
        configureControllers();
    }

    private void configureFilters() {
        bind(Filter.class)
                .annotatedWith(ThymesTemplateFilter.class).to(DefaultThymesTemplateFilter.class);
        bind(Filter.class).annotatedWith(ResourceFilter.class).to(DefaultResourceFilter.class);
    }

    private void configureControllers() {
        ThymeleafControllerPackageNames packageNames = new ThymeleafControllerPackageNames()
                .witchPackage(ThymeleafController.class);
        bind(ThymeleafControllerPackageNames.class).toInstance(packageNames);
        bind(ThymeleafControllerResolver.class).to(DefaultThymeleafControllerResolver.class);
        bind(ResourceResolver.class).to(DefaultResourceResolver.class);
        bind(ThymesApplicationVariablesBuilder.class).to(DefaultThymesApplicationVariablesBuilder.class);
        bind(ThymeleafControllerClassloaderBuilder.class).to(DefaultThymeleafControllerClassloaderBuilder.class);
    }
}
