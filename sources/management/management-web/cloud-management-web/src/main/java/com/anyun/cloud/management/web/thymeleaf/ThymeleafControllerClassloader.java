package com.anyun.cloud.management.web.thymeleaf;

import com.anyun.cloud.management.web.common.thymeleaf.ThymeleafController;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 23/06/2017
 */
public class ThymeleafControllerClassloader extends URLClassLoader {
    private Map<String, ThymeleafController> controllers = new HashMap<>();
    private Map<String,Class<?>> jaxrsApis = new HashMap<>();

    public ThymeleafControllerClassloader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ThymeleafControllerClassloader(URL[] urls) {
        super(urls);
    }

    public ThymeleafControllerClassloader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public Map<String, ThymeleafController> getControllers() {
        return controllers;
    }

    public void setControllers(Map<String, ThymeleafController> controllers) {
        this.controllers = controllers;
    }

    public Map<String, Class<?>> getJaxrsApis() {
        return jaxrsApis;
    }

    public void setJaxrsApis(Map<String, Class<?>> jaxrsApis) {
        this.jaxrsApis = jaxrsApis;
    }
}
