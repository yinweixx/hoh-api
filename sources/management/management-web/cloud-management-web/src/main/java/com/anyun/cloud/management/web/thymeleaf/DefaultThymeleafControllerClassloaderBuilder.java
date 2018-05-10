package com.anyun.cloud.management.web.thymeleaf;

import com.anyun.cloud.management.web.server.WebServerOptions;
import com.anyun.common.lang.StringUtils;
import com.anyun.common.lang.options.ApplicationOptions;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 23/06/2017
 */
public class DefaultThymeleafControllerClassloaderBuilder implements ThymeleafControllerClassloaderBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultThymeleafControllerClassloaderBuilder.class);
    private String jarsPath;
    private List<URL> allJarFileURLs;
    private ApplicationOptions options;

    @Inject
    public DefaultThymeleafControllerClassloaderBuilder(ApplicationOptions options) {
        this.options = options;
        allJarFileURLs = new ArrayList<>();
    }

    public DefaultThymeleafControllerClassloaderBuilder() {
        allJarFileURLs = new ArrayList<>();
    }

    public DefaultThymeleafControllerClassloaderBuilder withJarsPath(String jarsPath) {
        this.jarsPath = jarsPath;
        return this;
    }

    @Override
    public ThymeleafControllerClassloader build() throws Exception {
        if (StringUtils.isEmpty(jarsPath))
            jarsPath = options.getCommandLine().getOptionValue(WebServerOptions.WEB_DEPLOY_DIR);
        resolveJarsByDirectory(jarsPath);
        URL[] urls = new URL[allJarFileURLs.size()];
        for (int i = 0; i < urls.length; i++) {
            urls[i] = allJarFileURLs.get(i);
        }
        ThymeleafControllerClassloader thymeleafControllerClassloader = new ThymeleafControllerClassloader(urls);
        return thymeleafControllerClassloader;
    }

    private void resolveJarsByDirectory(String path) throws Exception {
        if (StringUtils.isEmpty(path))
            return;
        File file = new File(path);
        if (!file.exists())
            return;
        if (file.isFile()) {
            if (file.getName().endsWith(".jar")) {
                allJarFileURLs.add(file.toURI().toURL());
                LOGGER.debug("Add jar file: {}", file);
            }

        } else if (file.isDirectory()) {
            for (File aFile : file.listFiles()) {
                resolveJarsByDirectory(aFile.getAbsolutePath());
            }
        }
    }
}
