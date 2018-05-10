package com.anyun.cloud.management.web.common.thymeleaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/06/2017
 */
public class ThymeleafControllerPackageNames {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafControllerPackageNames.class);
    private List<String> controllerPackages = new ArrayList<>();

    public ThymeleafControllerPackageNames(List<String> controllerPackages) {
        this.controllerPackages = controllerPackages;
    }


    public ThymeleafControllerPackageNames() {
    }

    public ThymeleafControllerPackageNames withPackage(String packageName) {
        controllerPackages.add(packageName);
        LOGGER.debug("Add controller resolve parent package: {}", packageName);
        return this;
    }

    public ThymeleafControllerPackageNames witchPackage(Class<?> aClass) {
        return withPackage(aClass.getPackage().getName());
    }

    public List<String> getControllerPackages() {
        return controllerPackages;
    }

    public void setControllerPackages(List<String> controllerPackages) {
        this.controllerPackages = controllerPackages;
    }
}
